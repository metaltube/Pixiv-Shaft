package ceui.lisa.activities;

import android.content.Intent;
import android.view.KeyEvent;

import androidx.fragment.app.Fragment;

import ceui.lisa.fragments.FragmentAbout;
import ceui.lisa.fragments.FragmentAboutUser;
import ceui.lisa.fragments.FragmentBlank;
import ceui.lisa.fragments.FragmentBookTag;
import ceui.lisa.fragments.FragmentComment;
import ceui.lisa.fragments.FragmentFollowUser;
import ceui.lisa.fragments.FragmentLicense;
import ceui.lisa.fragments.FragmentLikeIllust;
import ceui.lisa.fragments.FragmentLocalUsers;
import ceui.lisa.fragments.FragmentMetro;
import ceui.lisa.fragments.FragmentMultiDownload;
import ceui.lisa.fragments.FragmentNew;
import ceui.lisa.fragments.FragmentNiceFriend;
import ceui.lisa.fragments.FragmentPivision;
import ceui.lisa.fragments.FragmentRecmdUser;
import ceui.lisa.fragments.FragmentRelatedIllust;
import ceui.lisa.fragments.FragmentSearch;
import ceui.lisa.fragments.FragmentSearchResult;
import ceui.lisa.fragments.FragmentSearchUser;
import ceui.lisa.fragments.FragmentSelectBookTag;
import ceui.lisa.fragments.FragmentSettings;
import ceui.lisa.fragments.FragmentUserIllust;
import ceui.lisa.fragments.FragmentUserManga;
import ceui.lisa.fragments.FragmentViewHistory;
import ceui.lisa.fragments.FragmentWalkThrough;
import ceui.lisa.fragments.FragmentWebView;
import ceui.lisa.fragments.FragmentWhoFollowThisUser;
import ceui.lisa.ui.fragment.FragmentT;
import ceui.lisa.utils.Params;
import ceui.lisa.utils.ReverseResult;

public class TemplateActivity extends FragmentActivity {

    public static final String EXTRA_FRAGMENT = "dataType";
    public static final String EXTRA_OBJECT = "object";
    public static final String EXTRA_KEYWORD = "keyword";
    public static final String EXTRA_ILLUST_ID = "illust id";
    public static final String EXTRA_ILLUST_TITLE = "illust title";

    @Override
    protected Fragment createNewFragment() {
        Intent intent = getIntent();
        String dataType = intent.getStringExtra(EXTRA_FRAGMENT);

        if (dataType != null) {
            switch (dataType) {
                case "搜索结果": {
                    String keyword = intent.getStringExtra(EXTRA_KEYWORD);
                    return FragmentSearchResult.newInstance(keyword);
                }
                case "相关作品": {
                    int id = intent.getIntExtra(EXTRA_ILLUST_ID, 0);
                    String title = intent.getStringExtra(EXTRA_ILLUST_TITLE);
                    return FragmentRelatedIllust.newInstance(id, title);
                }
                case "浏览记录":
                    return new FragmentViewHistory();
                case "网页链接": {
                    String url = intent.getStringExtra("url");
                    String title = intent.getStringExtra("title");
                    return FragmentWebView.newInstance(title, url);
                }
                case "设置":
                    return new FragmentSettings();
                case "推荐用户":
                    return new FragmentRecmdUser();
                case "特辑":
                    return new FragmentPivision();
                case "搜索用户": {
                    String keyword = intent.getStringExtra(EXTRA_KEYWORD);
                    return FragmentSearchUser.newInstance(keyword);
                }
                case "以图搜图":
                    ReverseResult result = intent.getParcelableExtra("result");
                    return FragmentWebView.newInstance(result.getTitle(), result.getUrl(), result.getResponseBody(), result.getMime(), result.getEncoding(), result.getHistory_url());
                case "相关评论": {
                    int id = intent.getIntExtra(EXTRA_ILLUST_ID, 0);
                    String title = intent.getStringExtra(EXTRA_ILLUST_TITLE);
                    return FragmentComment.newInstance(id, title);
                }
                case "账号管理":
                    return new FragmentLocalUsers();
                case "地铁表白器":
                    return new FragmentMetro();
                case "按标签筛选": {
                    String keyword = intent.getStringExtra(EXTRA_KEYWORD);
                    return FragmentBookTag.newInstance(keyword);
                }
                case "按标签收藏": {
                    int id = intent.getIntExtra(EXTRA_ILLUST_ID, 0);
                    return FragmentSelectBookTag.newInstance(id);
                }
                case "关于软件":
                    return new FragmentAbout();
                case "批量下载":
                    return new FragmentMultiDownload();
                case "画廊":
                    return new FragmentWalkThrough();
                case "License":
                    return new FragmentLicense();
                case "正在关注":
                    return FragmentFollowUser.newInstance(
                            getIntent().getIntExtra(Params.USER_ID, 0),
                            FragmentLikeIllust.TYPE_PUBLUC, true);
                case "好P友":
                    return new FragmentNiceFriend();
                case "搜索":
                    return new FragmentSearch();
                case "详细信息":
                    return new FragmentAboutUser();
                case "一言":
                    return new FragmentT();
                case "最新作品":
                    return new FragmentNew();
                case "粉丝":
                    return FragmentWhoFollowThisUser.newInstance(intent.getIntExtra(Params.USER_ID, 0));
                case "插画作品":
                    return FragmentUserIllust.newInstance(intent.getIntExtra(Params.USER_ID, 0),
                            true);
                case "漫画作品":
                    return FragmentUserManga.newInstance(intent.getIntExtra(Params.USER_ID, 0),
                            true);
                case "插画/漫画收藏":
                    return FragmentLikeIllust.newInstance(intent.getIntExtra(Params.USER_ID, 0),
                            FragmentLikeIllust.TYPE_PUBLUC, true);
                default:
                    return new FragmentBlank();
            }
        }
        return null;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (childFragment instanceof FragmentWebView) {
            return ((FragmentWebView) childFragment).getAgentWeb().handleKeyEvent(keyCode, event) ||
                    super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean hideStatusBar() {
        return super.hideStatusBar() && getIntent().getBooleanExtra("hideStatusBar", true);
    }
}