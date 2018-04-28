package com.novembergave.bakingapp;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.novembergave.bakingapp.pojo.Step;

public class ViewStepFragment extends Fragment {

  private static final String CLASS = ViewStepFragment.class.getName();
  private static final String ARG_STEP = CLASS + ".arg_step";
  private static final String ARG_STEP_POSITION = CLASS + ".arg_step_position";
  private static final String ARG_NUMBER_OF_STEPS = CLASS + ".arg_number_of_steps";
  private static final String STATE_PLAYBACK_POSITION = CLASS + ".state_playback_position";
  private static final String STATE_CURRENT_WINDOW = CLASS + ".state_current_window";
  private static final String STATE_PLAY_WHEN_READY = CLASS + ".state_play_when_ready";

  public static ViewStepFragment newInstance(Step step, int stepPosition, int numberOfSteps) {
    ViewStepFragment fragment = new ViewStepFragment();
    Bundle bundle = new Bundle();
    bundle.putParcelable(ARG_STEP, step);
    bundle.putInt(ARG_STEP_POSITION, stepPosition);
    bundle.putInt(ARG_NUMBER_OF_STEPS, numberOfSteps);
    fragment.setArguments(bundle);
    return fragment;
  }

  public interface OnNavigationSelected {
    void onNavigationSelected(int stepPosition);
  }

  private PlayerView playerView;
  private TextView stepView;
  private Step step;

  private SimpleExoPlayer player;
  private Uri uri;
  private long playbackPosition;
  private int currentWindow;
  private boolean playWhenReady;
  private OnNavigationSelected callback;

  @Override
  public void onResume() {
    super.onResume();
    if (uri != null)
      initializePlayer(uri);
  }

  @Override
  public void onPause() {
    super.onPause();
    if (Util.SDK_INT <= 23) {
      releasePlayer();
    }
  }

  @Override
  public void onStop() {
    super.onStop();
    if (Util.SDK_INT > 23) {
      releasePlayer();
    }
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    // This makes sure that the container activity has implemented
    // the callback interface. If not, it throws an exception
    try {
      callback = (OnNavigationSelected) context;
    } catch (ClassCastException e) {
      throw new ClassCastException(context.toString()
          + " must implement OnNavigationSelected");
    }
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putLong(STATE_PLAYBACK_POSITION, playbackPosition);
    outState.putInt(STATE_CURRENT_WINDOW, currentWindow);
    outState.putBoolean(STATE_PLAY_WHEN_READY, playWhenReady);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_view_step, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    Bundle args = getArguments();
    step = args.getParcelable(ARG_STEP);
    int numberOfSteps = args.getInt(ARG_NUMBER_OF_STEPS);
    int stepPosition = args.getInt(ARG_STEP_POSITION);

    playbackPosition = C.TIME_UNSET;

    if (savedInstanceState != null) {
      playbackPosition = savedInstanceState.getLong(STATE_PLAYBACK_POSITION);
      currentWindow = savedInstanceState.getInt(STATE_CURRENT_WINDOW);
      playWhenReady = savedInstanceState.getBoolean(STATE_PLAY_WHEN_READY);
    }

    playerView = view.findViewById(R.id.view_step_video);
    stepView = view.findViewById(R.id.view_step_description);
    stepView.setText(step.getDescription());

    setUpNavigation(view, stepPosition, numberOfSteps);

    if (!getUrl().trim().isEmpty()) {
      setUpMediaPlayer();
    } else {
      hideMediaPlayer();
    }
  }

  private void setUpNavigation(View view, int stepPosition, int numberOfSteps) {
    View navigationHolder = view.findViewById(R.id.view_step_navigation);
    ImageView backButton = view.findViewById(R.id.view_step_back);
    ImageView forwardButton = view.findViewById(R.id.view_step_forward);

    // If there are no steps, hide the holder
    if (numberOfSteps <= 0) {
      navigationHolder.setVisibility(View.GONE);
      return;
    }

    // else set up the navigation
    if (stepPosition == numberOfSteps-1) {
      forwardButton.setEnabled(false);
    } else if (step.getId() == 0) {
      backButton.setEnabled(false);
    }
    forwardButton.setOnClickListener(click -> callback.onNavigationSelected(stepPosition + 1));
    backButton.setOnClickListener(click -> callback.onNavigationSelected(stepPosition - 1));
  }

  private void hideMediaPlayer() {
    playerView.setVisibility(View.GONE);
  }

  private void setUpMediaPlayer() {
    playerView.setVisibility(View.VISIBLE);
    uri = Uri.parse(getUrl());
    if (uri != null && !Uri.EMPTY.equals(uri)) {
      initializePlayer(uri);
    } else {
      handleError();
    }
  }

  private void initializePlayer(Uri uri) {
    if (player == null) {
      player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getContext()),
          new DefaultTrackSelector(), new DefaultLoadControl());
      playerView.setPlayer(player);
      player.setPlayWhenReady(playWhenReady);
      player.seekTo(currentWindow, playbackPosition);
    }
    MediaSource mediaSource = buildMediaSource(uri);
    player.prepare(mediaSource, false, false);
  }

  private void handleError() {
    new AlertDialog.Builder(getContext())
        .setMessage(R.string.error_video)
        .setPositiveButton(R.string.action_close, (d, a) -> getActivity().finish())
        .create().show();
  }

  private void releasePlayer() {
    if (player != null) {
      playbackPosition = player.getCurrentPosition();
      currentWindow = player.getCurrentWindowIndex();
      playWhenReady = player.getPlayWhenReady();
      player.release();
      player = null;
    }
  }

  private MediaSource buildMediaSource(Uri uri) {
    return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("exoplayer"))
        .createMediaSource(uri);
  }

  private String getUrl() {
    return step.getVideoURL() != null ? step.getVideoURL() : step.getThumbnailURL();
  }
}
