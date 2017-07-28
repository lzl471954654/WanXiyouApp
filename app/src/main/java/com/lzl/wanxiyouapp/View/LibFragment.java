package com.lzl.wanxiyouapp.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.lzl.wanxiyouapp.R;

/**
 * Created by LZL on 2017/7/27.
 */

public class LibFragment extends Fragment {
    WebView webView;
    View root;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.library,container,false);
        webView = (WebView) root.findViewById(R.id.lib_webView);
        webView.loadUrl("http://lib.xupt.edu.cn/wap/index.html#xylib_search_1");
        webView.getSettings().setJavaScriptEnabled(true);
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
