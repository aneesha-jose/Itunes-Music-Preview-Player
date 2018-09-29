package com.mplayer.itunesmusicpreviewplayer.searchTracks;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mplayer.itunesmusicpreviewplayer.DependencyInjectorComponent;
import com.mplayer.itunesmusicpreviewplayer.R;
import com.mplayer.itunesmusicpreviewplayer.base.ViewModelFactory;
import com.mplayer.itunesmusicpreviewplayer.base.activity.BaseActivity;
import com.mplayer.itunesmusicpreviewplayer.common.CustomObserver;
import com.mplayer.itunesmusicpreviewplayer.common.view.DrawablePagerIndicatorDecoration;
import com.mplayer.itunesmusicpreviewplayer.data.models.ItuneEntity;
import com.mplayer.itunesmusicpreviewplayer.data.models.SearchedWords;
import com.mplayer.itunesmusicpreviewplayer.player.TrackPlayerActivity;
import com.mplayer.itunesmusicpreviewplayer.searchTracks.pojos.ItuneEntityBlockWrapper;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;

import static butterknife.OnTextChanged.Callback.AFTER_TEXT_CHANGED;

public class SearchTracksActivity extends BaseActivity implements ItunesEntityBlockAdapter.Listener {

    public static Intent createInstance(Context context) {
        Intent intent = new Intent(context, SearchTracksActivity.class);
        //Since it acts as a landing home page
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

    @Inject
    ViewModelFactory viewModelFactory;

    @Inject
    Picasso picasso;

    @BindView(R.id.et_search)
    AppCompatAutoCompleteTextView etSearch;

    @BindView(R.id.tv_total_count)
    TextView tvTotalCount;

    @BindView(R.id.rv_track_blocks)
    RecyclerView rvTrackBlocks;

    private ArrayAdapter<SearchedWords> searchedWordsArrayAdapter;
    private ItunesEntityBlockAdapter blockAdapter;
    private ItuneEntityBlockWrapperViewModel ituneEntityBlockWrapperViewModel;

    private CustomObserver.ChangeListener<List<ItuneEntityBlockWrapper>> blockWrapperChangeObserver = new CustomObserver.ChangeListener<List<ItuneEntityBlockWrapper>>() {
        @Override
        public void onSuccess(List<ItuneEntityBlockWrapper> dataWrapper) {
            dismissLoadingDialog();
            if (blockAdapter == null)
                setUpIntentBlockRecyclerView();
            blockAdapter.setItems(dataWrapper);
        }

        @Override
        public void onException(Throwable exception) {
            dismissLoadingDialog();
            showToast(R.string.error_while_searching_for_tracks);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tracks);

        initView();
    }

    private void initViewModel() {
        ituneEntityBlockWrapperViewModel = ViewModelProviders.of(this, viewModelFactory).get(ItuneEntityBlockWrapperViewModel.class);
        ituneEntityBlockWrapperViewModel.getTrackData().observe(this, new CustomObserver<>(blockWrapperChangeObserver));
        ituneEntityBlockWrapperViewModel.getAllSongsCount().observe(this, this::updateTotalCount);
        ituneEntityBlockWrapperViewModel.getAllSearchedWords().observe(this, this::updateSearchWords);
    }

    private void initView() {
        setupSearchView();
        setUpIntentBlockRecyclerView();
        initViewModel();
    }

    private void updateTotalCount(String count) {
        if (tvTotalCount != null)
            tvTotalCount.setText(getString(R.string.all_tracks, count));
    }

    private void updateSearchWords(List<SearchedWords> words) {
        if (searchedWordsArrayAdapter == null)
            setupSearchView();
        searchedWordsArrayAdapter.addAll(words);
        searchedWordsArrayAdapter.notifyDataSetChanged();
    }

    private void setupSearchView() {
        searchedWordsArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, android.R.id.text1);
        etSearch.setAdapter(searchedWordsArrayAdapter);
        etSearch.setThreshold(0);
    }

    @OnTextChanged(value = R.id.et_search, callback = AFTER_TEXT_CHANGED)
    public void onSearchTextChanged(Editable editable) {
        if (searchedWordsArrayAdapter != null)
            searchedWordsArrayAdapter.getFilter().filter(editable.toString().trim());
    }

    @OnEditorAction(R.id.et_search)
    boolean onActionSearch(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
            onSearchClicked(etSearch.getText().toString());
            return true;
        }
        return false;
    }

    private void onSearchClicked(String searchWord) {
        ituneEntityBlockWrapperViewModel.addSearchWord(searchWord);
        showLoadingDialog("", false);
        ituneEntityBlockWrapperViewModel.searchTracks(searchWord, sharedPref.getScreenSizeCount());
    }

    private void setUpIntentBlockRecyclerView() {
        blockAdapter = new ItunesEntityBlockAdapter(this, picasso);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayout.HORIZONTAL, false);
        // Set the required LayoutManager
        rvTrackBlocks.setLayoutManager(layoutManager);
        // set true if your RecyclerView is finite and has fixed size
        rvTrackBlocks.setHasFixedSize(false);
        // Initialize and set the RecyclerView Adapter
        rvTrackBlocks.setAdapter(blockAdapter);
        // add pager behavior
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rvTrackBlocks);

        rvTrackBlocks.addItemDecoration(new DrawablePagerIndicatorDecoration(ContextCompat.getDrawable(context, R.drawable.page_selected_indicator), ContextCompat.getDrawable(context, R.drawable.page_unselected_indicator)));
    }

    @Override
    protected void callDependencyInjector(DependencyInjectorComponent injectorComponent) {
        injectorComponent.injectDependencies(this);
    }

    @Override
    public void onItuneTrackClicked(ItuneEntity track) {
        startActivity(TrackPlayerActivity.createInstance(this, track));
        overridePendingTransition(R.anim.slide_up_bottom, R.anim.stay);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.exit_to_right, R.anim.stay);
    }
}
