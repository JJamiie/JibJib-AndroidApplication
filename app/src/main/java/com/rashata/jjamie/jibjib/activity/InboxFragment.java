package com.rashata.jjamie.jibjib.activity;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rashata.jjamie.jibjib.R;
import com.rashata.jjamie.jibjib.manager.RESTAPIRetrofit;
import com.rashata.jjamie.jibjib.serializer.Question;
import com.rashata.jjamie.jibjib.adapter.RVQuestionAdapter;
import com.rashata.jjamie.jibjib.util.MyHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InboxFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InboxFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InboxFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "InboxFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String token;

    private RecyclerView recycle_view_my_question;
    private OnFragmentInteractionListener mListener;
    private ArrayList<Question> my_questions;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RVQuestionAdapter rvQuestionAdapter;

    public InboxFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InboxFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InboxFragment newInstance(String param1, String param2) {
        InboxFragment fragment = new InboxFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            token = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        my_questions = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inbox, container, false);
        bindWidget(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadMyQuestion();

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void bindWidget(View view) {
        recycle_view_my_question = (RecyclerView) view.findViewById(R.id.recycle_view_my_question);
        recycle_view_my_question.setHasFixedSize(true);

        // To manage the positioning of its items
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recycle_view_my_question.setLayoutManager(llm);

        my_questions = new ArrayList<Question>();
        rvQuestionAdapter = new RVQuestionAdapter(my_questions, getActivity(), token);
        recycle_view_my_question.setAdapter(rvQuestionAdapter);

        // set refresh when scroll recycle
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout_inbox);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });
    }


    public void refreshItems() {
        // Load items
        // ...
        loadMyQuestion();
        // Load complete
        onItemsLoadComplete();
    }

    public void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...

        // Stop refresh animation
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void loadMyQuestion() {
        AsyncTask<Void, Void, Void> loadMyQuestionTask = new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... params) {

//                String BASE_URL = "http://192.168.1.34:8000";
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(MyHelper.getInstance().getBaseUrl())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RESTAPIRetrofit restapiRetrofit = retrofit.create(RESTAPIRetrofit.class);
                Call<List<Question>> call = restapiRetrofit.getMyQuestions(token);
                call.enqueue(new Callback<List<Question>>() {

                    @Override
                    public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                        my_questions.clear();
                        my_questions.addAll(response.body());
                        rvQuestionAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<List<Question>> call, Throwable t) {

                    }
                });
                return null;
            }
        };
        loadMyQuestionTask.execute();
    }


}
