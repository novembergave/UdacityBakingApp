package com.novembergave.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.novembergave.bakingapp.pojo.Step;

import static com.novembergave.bakingapp.utils.FormatUtils.removeStepNumber;

public class ViewStepPhoneFragment extends Fragment {

  private static final String CLASS = ViewStepPhoneFragment.class.getName();
  private static final String ARG_STEP = CLASS + ".arg_step";

  public static ViewStepPhoneFragment newInstance(Step step) {
    ViewStepPhoneFragment fragment = new ViewStepPhoneFragment();
    Bundle bundle = new Bundle();
    bundle.putParcelable(ARG_STEP, step);
    fragment.setArguments(bundle);
    return fragment;
  }

  private TextView stepView;
  private Step step;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_view_step, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    Bundle args = getArguments();
    step = args.getParcelable(ARG_STEP);

    stepView = view.findViewById(R.id.view_step_description);
    stepView.setText(step.getDescription());
  }
}
