package com.fanwe.o2o.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.handler.PhotoHandler;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDToast;
import com.fanwe.o2o.R;
import com.fanwe.o2o.appview.AccountManageStateView;
import com.fanwe.o2o.appview.CommonRowView;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.dialog.MoreTitleDialog;
import com.fanwe.o2o.event.ERefreshRequest;
import com.fanwe.o2o.event.EUpLoadUserHeadImageComplete;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.model.AccountManageActModel;
import com.fanwe.o2o.model.AccountManageGroupInfoModel;
import com.fanwe.o2o.model.AccountManageLevelInfoModel;
import com.fanwe.o2o.model.AccountManageUserInfoModel;
import com.fanwe.o2o.utils.PhotoBotShowUtils;
import com.fanwe.o2o.utils.SDNumberUtil;

import java.io.File;
import java.util.List;

import org.xutils.view.annotation.ViewInject;

import static com.fanwe.o2o.appview.AccountManageStateView.STYLE_EXP;
import static com.fanwe.o2o.appview.AccountManageStateView.STYLE_VIP;

/**
 * Created by heyucan on 2017/1/15.
 */

public class AccountManageAcitivty extends BaseTitleActivity {
//    @ViewInject(R.id.amsv_1)
//    private AccountManageStateView amsv1;
//    @ViewInject(R.id.amsv_2)
//    private AccountManageStateView amsv2;
    @ViewInject(R.id.crv_row1)
    private CommonRowView crv_row1;
    @ViewInject(R.id.crv_row2)
    private CommonRowView crv_row2;
//    @ViewInject(R.id.crv_row3)
//    private CommonRowView crv_row3;
    @ViewInject(R.id.crv_row4)
    private CommonRowView crv_row4;
    @ViewInject(R.id.crv_row5)
    private CommonRowView crv_row5;

    private boolean isPhoneUnbind = false;
    private boolean hasChangeName = false;
    private String nickName;
    private String mobile;
    private String startMobile;//手机号前4位
    private String endMobile;//手机号后3位

    private MoreTitleDialog titleDialog;
    private PhotoHandler mHandler;
    private String head_image;

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        setContentView(R.layout.act_account_manage_1);
        initTitle();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestAccountManage();
    }

    private void initView() {
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 0);
//        amsv1.setLayoutParams(lp);
//        amsv2.setLayoutParams(lp);
        crv_row1.setOnClickListener(this);
        crv_row2.setOnClickListener(this);
        crv_row4.setOnClickListener(this);
        crv_row5.setOnClickListener(this);

        mHandler = new PhotoHandler(this);
        mHandler.setListener(new PhotoHandler.PhotoHandlerListener() {
            @Override
            public void onResultFromAlbum(File file) {
                openCropAct(file);
            }

            @Override
            public void onResultFromCamera(File file) {
                openCropAct(file);
            }

            @Override
            public void onFailure(String msg) {
                SDToast.showToast(msg);
            }
        });
    }

    private void openCropAct(File file) {
        Intent intent = new Intent(this, UploadUserHeadImageActivity.class);
        intent.putExtra(UploadUserHeadImageActivity.EXTRA_IMAGE_URL, file.getAbsolutePath());
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mHandler.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 接收图片选择回传地址事件
     *
     * @param event
     */
    public void onEventMainThread(EUpLoadUserHeadImageComplete event) {
        head_image = event.head_image;
        crv_row1.setCiv_right(head_image);
    }

    private void initTitle() {
        title.setMiddleTextTop("账户管理");

        title.initRightItem(1);
        title.getItemRight(0).setImageRight(R.drawable.ic_title_more);
    }

    @Override
    public void onCLickRight_SDTitleSimple(SDTitleItem v, int index) {
        super.onCLickRight_SDTitleSimple(v, index);
        if (titleDialog == null)
            titleDialog = new MoreTitleDialog(this);
        titleDialog.requestData();
        titleDialog.showTop();
    }

    private void requestAccountManage() {
        showProgressDialog("");
        CommonInterface.requestAccountManage(new AppRequestCallback<AccountManageActModel>() {
            @Override
            protected void onSuccess(SDResponse sdResponse) {
                if (actModel.isOk()) {
                    AccountManageUserInfoModel userInfoModel = actModel.getUser_info();
                    List<AccountManageGroupInfoModel> group = actModel.getGroup_info();
                    List<AccountManageLevelInfoModel> level = actModel.getLevel_info();
                    final String ghighest = actModel.getGhighest();
                    final String phighest = actModel.getPhighest();
                    AccountManageGroupInfoModel groupInfo1 = group.get(0);
                    AccountManageGroupInfoModel groupInfo2 = group.get(1);
                    AccountManageLevelInfoModel levelInfo1 = level.get(0);
                    AccountManageLevelInfoModel levelInfo2 = level.get(1);
//                    amsv1
//                            .setTxts(amsv1.getTxtsMap(actModel, STYLE_VIP))
//                            .setProgress(ghighest.equals("1") ? str2Float(userInfoModel.getTotal_score()) //ghighest 1:会员等级最高级， 0：非最高级
//                                            : str2Float(userInfoModel.getTotal_score()) - str2Float(groupInfo1.getScore()),
//                                    ghighest.equals("1") ? str2Float(userInfoModel.getTotal_score())
//                                            : str2Float(groupInfo2.getScore())
//                                            - str2Float(
//                                            groupInfo1.getScore()));
//                    amsv2
//                            .setTxts(amsv2.getTxtsMap(actModel, STYLE_EXP))
//                            .setProgress(phighest.equals("1") ? str2Float(userInfoModel.getPoint()) //phighest 1:jingyan等级最高级， 0：非最高级
//                                            : str2Float(userInfoModel.getPoint()) - str2Float(levelInfo1.getPoint()),
//                                    phighest.equals("1") ? str2Float(userInfoModel.getPoint())
//                                            : str2Float(levelInfo2.getPoint())
//                                            - str2Float(
//                                            levelInfo1.getPoint()));
                    crv_row1.setCiv_right(userInfoModel.getUser_avatar());
                    crv_row2.setTv_right(userInfoModel.getUser_name());
//                    if (TextUtils.isEmpty(userInfoModel.getEmail())) {
//                        crv_row3.setVisibility(View.GONE);
//                    } else {
//                        crv_row3.setTv_right(userInfoModel.getEmail());
//                    }

                    mobile = userInfoModel.getMobile();
                    startMobile = mobile.substring(0, 4);
                    endMobile = mobile.substring(8);
                    isPhoneUnbind = TextUtils.isEmpty(mobile);
                    crv_row4.setTv_right(
                            isPhoneUnbind ? "未绑定" : startMobile + "****" + endMobile)
                            .setTv_rightTxtColor(R.color.text_content);
//                    crv_row5.setTv_right("******").setTv_rightTxtColor(R.color.text_content);

                    String is_tmp = userInfoModel.getIs_tmp();//1.为改名字 0.不能改
                    if (!TextUtils.isEmpty(is_tmp) && is_tmp.equals("1")) {
                        hasChangeName = false;
                        crv_row2.setEnabled(true);
                        crv_row2.showIv_right_action_img(true);
                    } else if (!TextUtils.isEmpty(is_tmp) && is_tmp.equals("0")) {
                        hasChangeName = true;
                        crv_row2.setEnabled(false);
                        crv_row2.showIv_right_action_img(false);
                    }
                    nickName = userInfoModel.getUser_name();
                }
            }

            @Override
            protected void onError(SDResponse resp) {
                super.onError(resp);
            }

            @Override
            protected void onFinish(SDResponse resp) {
                super.onFinish(resp);
                dismissProgressDialog();
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.crv_row1:
                //上传头像
                clickPhoto();
                break;
            case R.id.crv_row2:
                //修改昵称
                if (!hasChangeName) {
                    clickUpdateNickname();
                }
                break;
            case R.id.crv_row4:
                if (isPhoneUnbind) {
                    //未绑定时,可以跳转到绑定手机页面
                    clickBindMobile();
                } else {
                    clickUpdateBindMobile();
                }
                break;
            case R.id.crv_row5:
                //跳转到修改密码页面
                if (isPhoneUnbind) {
                    //未绑定时,可以跳转到绑定手机页面
                    clickBindMobile();
                } else {
                    clickModifyPwd();
                }
                break;
        }
    }

    public float str2Float(String s) {
        return SDNumberUtil.parseFloat(s);
    }

    /**
     * 上传图片
     */
    private void clickPhoto() {
        PhotoBotShowUtils.openBotPhotoView(AccountManageAcitivty.this, mHandler, PhotoBotShowUtils.DIALOG_BOTH);
    }

    /**
     * 修改昵称
     */
    private void clickUpdateNickname() {
        Intent intent = new Intent(this, UpdateNickNameActivity.class);
        intent.putExtra(UpdateNickNameActivity.EXTRA_NICK_NAME, nickName);
        startActivity(intent);
    }

    /**
     * 绑定手机号
     */
    private void clickBindMobile() {
        Intent intent = new Intent(this, BindMobileActivity.class);
        startActivity(intent);
    }

    /**
     * 更改绑定手机号
     */
    private void clickUpdateBindMobile() {
        Intent intent = new Intent(this, BindMobileActivity.class);
        intent.putExtra(BindMobileActivity.EXTRA_TITLE, BindMobileActivity.EXTRA_VALUE);
        intent.putExtra(BindMobileActivity.EXTRA_MOBILE, mobile);
        startActivity(intent);
    }

    /**
     * 修改密码
     */
    private void clickModifyPwd() {
        Intent intent = new Intent(this, ModifyPwdActivity.class);
        intent.putExtra(ModifyPwdActivity.EXTRA_TITLE, "修改密码");
        intent.putExtra(ModifyPwdActivity.EXTRA_MOBILE, mobile);
        startActivity(intent);
    }

    public void onEventMainThread(ERefreshRequest event) {
        requestAccountManage();
    }
}
