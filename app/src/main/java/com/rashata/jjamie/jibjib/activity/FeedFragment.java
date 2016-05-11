package com.rashata.jjamie.jibjib.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

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
 * {@link FeedFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "FeedFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private RecyclerView recycler_view_question;
    private ArrayList<Question> questions;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Button btn_from_language;
    private Button btn_to_language;
    private Button btn_create_que;
    private String token;
    private RVQuestionAdapter rv_adapter;
    private ArrayAdapter<String> arrayAdapterFrom;
    private ArrayAdapter<String> arrayAdapterTo;


    public FeedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FeedFragment newInstance(String param1, String param2) {
        FeedFragment fragment = new FeedFragment();
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
        questions = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        setDialogAdapter();
        bindWidget(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadQuestion();

    }

    public void bindWidget(View view) {
        recycler_view_question = (RecyclerView) view.findViewById(R.id.recycle_view_question);
        recycler_view_question.setHasFixedSize(true);

        // To manage the positioning of its items
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recycler_view_question.setLayoutManager(llm);

        questions = new ArrayList<>();
        rv_adapter = new RVQuestionAdapter(questions, getActivity(), token);
        recycler_view_question.setAdapter(rv_adapter);

        // set refresh when scroll recycle
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });

        btn_from_language = (Button) view.findViewById(R.id.btn_from_language);
        btn_from_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFromDialog();
            }
        });
        btn_to_language = (Button) view.findViewById(R.id.btn_to_language);
        btn_to_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToDialog();
            }
        });

        btn_create_que = (Button) view.findViewById(R.id.btn_create_ques);
        btn_create_que.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateTranslation.class);
                intent.putExtra("token", token);
                startActivity(intent);
            }
        });
    }

    public void setDialogAdapter() {
        arrayAdapterFrom = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item);
        arrayAdapterFrom.add("Thai");
        arrayAdapterFrom.add("English");
        arrayAdapterFrom.add("Chinese");
        arrayAdapterTo = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item);
        arrayAdapterTo.add("Thai");
        arrayAdapterTo.add("English");
        arrayAdapterTo.add("Chinese");
    }

    public void showFromDialog() {
        final AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
        builderSingle.setAdapter(
                arrayAdapterFrom,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapterFrom.getItem(which);
                        btn_from_language.setText(strName);
                        setDialogAdapter();
                        arrayAdapterTo.remove(strName);
                    }
                });
        builderSingle.show();
    }

    public void showToDialog() {
        final AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
        builderSingle.setAdapter(
                arrayAdapterTo,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapterTo.getItem(which);
                        btn_to_language.setText(strName);
                        setDialogAdapter();
                        arrayAdapterFrom.remove(strName);
                    }
                });
        builderSingle.show();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    public void refreshItems() {
        // Load items
        // ...
        loadQuestion();
        // Load complete
        onItemsLoadComplete();
    }

    public void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...

        // Stop refresh animation
        mSwipeRefreshLayout.setRefreshing(false);
    }


    public void showChooseLanguageDialog(final Button button) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item);
        arrayAdapter.add("Thai");
        arrayAdapter.add("English");
        arrayAdapter.add("Chinese");

        builderSingle.setAdapter(
                arrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                        button.setText(strName);
                    }
                });
        builderSingle.show();
    }

    public void loadQuestion() {
        AsyncTask<Void, Void, Void> loadAllQuestionTask = new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(final Void... params) {
//                String BASE_URL = "http://192.168.1.34:8000";
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(MyHelper.getInstance().getBaseUrl())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RESTAPIRetrofit restapiRetrofit = retrofit.create(RESTAPIRetrofit.class);
                Call<List<Question>> call = restapiRetrofit.getAllQuestions();
                call.enqueue(new Callback<List<Question>>() {

                    @Override
                    public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                        questions.clear();
                        questions.addAll(response.body());
                        rv_adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<List<Question>> call, Throwable t) {

                    }
                });
                return null;
            }
        };
        loadAllQuestionTask.execute();
    }

}
