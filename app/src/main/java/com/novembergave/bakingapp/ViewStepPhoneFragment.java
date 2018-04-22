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

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.novembergave.bakingapp.pojo.Step;

public class ViewStepPhoneFragment extends Fragment {

  private static final String CLASS = ViewStepPhoneFragment.class.getName();
  private static final String ARG_STEP = CLASS + ".arg_step";
  private static final String STATE_PLAYBACK_POSITION = CLASS + ".state_playback_position";
  private static final String STATE_CURRENT_WINDOW = CLASS + ".state_current_window";
  private static final String STATE_PLAY_WHEN_READY = CLASS + ".state_play_when_ready";

  public static ViewStepPhoneFragment newInstance(Step step) {
    ViewStepPhoneFragment fragment = new ViewStepPhoneFragment();
    Bundle bundle = new Bundle();
    bundle.putParcelable(ARG_STEP, step);
    fragment.setArguments(bundle);
    return fragment;
  }

  private PlayerView playerView;
  private TextView stepView;
  private Step step;

  private SimpleExoPlayer player;
  private Uri uri;
  private long playbackPosition;
  private int currentWindow;
  private boolean playWhenReady;

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

    playbackPosition = C.TIME_UNSET;

    if (savedInstanceState != null) {
      playbackPosition = savedInstanceState.getLong(STATE_PLAYBACK_POSITION);
      currentWindow = savedInstanceState.getInt(STATE_CURRENT_WINDOW);
      playWhenReady = savedInstanceState.getBoolean(STATE_PLAY_WHEN_READY);
    }

    playerView = view.findViewById(R.id.view_step_video);
    stepView = view.findViewById(R.id.view_step_description);
    stepView.setText(step.getDescription());

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
    return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("exoplayer-codelab"))
        .createMediaSource(uri);
  }

  private String getUrl() {
    return step.getVideoURL() != null ? step.getVideoURL() : step.getThumbnailURL();
  }
}
