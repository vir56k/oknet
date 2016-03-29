oknet是一套基于okhttp的android网络http框架。

#特性
1.简洁的语法
2.支持自定义处理 message code 不等于0 的情形
3.支持文件上传
4.完整清晰的log日志输出
5.支持 公共参数 的配置
6.支持每个http请求的 日志 记录
7.支持 默认异常 的处理
8.支持 移除文件下载（通过FileDownloader）

#适用场景
和服务端产生约定：
响应的json格式一定为：{code:0,   msg:"", body:""}
  1.服务端 响应成功 则返回对应的json
  2.code=0表示成功，body里如正确响应json.
  3.code非零表示失败，msg表示失败的文本。

#post简单请求，和String类型的响应
 RequestBuilder.with(getActivity())
                .URL(Apis.GAEA_URLS.CAB_ADVERT_LIST)
                .onSuccess(new CommonCallback<String>(String.class) {
                    @Override
                    public void onSuccess(String result, CommonMessage responseMessage, String responseString) {
                        Log.i(TAG, "==成功:" + result);
                        alert("==成功");
                    }
                })
                .excute();
#带参数的请求，和 Json序列化的回调

        Type t = new TypeToken<List<Demo2Cell>>() {
        }.getType();
        RequestBuilder.with(getActivity())
                .URL(Apis.Cab_Urls.GET_BOX_FREE_NEWS)
                .para("cabinet_code", "1412345678")
                .onSuccess(new CommonCallback<List<Demo2Cell>>(t) {
                    @Override
                    public void onSuccess(List<Demo2Cell> result, CommonMessage responseMessage, String responseString) {
                        Log.i(TAG, "!!! 成功:" + result.get(0));
                        alert("!!成功" + result.get(0));
                    }
                })
                .excute();

#自定义处理 message code 不等于0 的情形
 RequestBuilder.with(getActivity())
                .URL(Apis.GAEA_URLS.CAB_ADVERT_LIST)
                .onSuccess(new CommonCallback<String>(String.class) {
                    @Override
                    public void onSuccess(String result, CommonMessage responseMessage, String responseString) {
                        Log.i(TAG, "==成功:" + result);
                        alert("==成功");
                    }

                    @Override
                    public boolean onFailure(int httpCode, Exception ex, CommonMessage responseMessage, String responseString) {
                        if (ex instanceof NoZeroException) {
                            NoZeroException noZeroException = (NoZeroException) ex;
                            int code = noZeroException.getCode();
                            Log.i(TAG, "!!!!!!!!失败:" + noZeroException);
                            alert("!!!!!!!!!!!!!!!!失败," + noZeroException);
//                          return false;//如果不需要 默认异常处理器再次处理，这里可以返回true
                        }
                        return super.onFailure(httpCode, ex, responseMessage, responseString);
                    }
                })
                .excute();
#上传文件

 File f = new File(Environment.getExternalStorageDirectory().getPath(), "ImageCache/CloseIcon.png");
        if (!f.exists())
            throw new RuntimeException("not found ImageCache/CloseIcon.png");
        RequestBuilder.with(getActivity())
                .URL("http://10.0.1.232:8888/uc/suser/upload_avatar")
                .para("uid", "100202")
                .para("sid", "50e2904ca493d5d25475e4e080858925")
                        /************************ 威力仅仅在这一行,其他都一样 ***************************/
                .file("file", f)
                        /************************ 威力仅仅在这一行,其他都一样 ***************************/
                .onSuccess(new CommonCallback<Demo3Bean>(Demo3Bean.class) {
                    @Override
                    public void onSuccess(Demo3Bean result, CommonMessage responseMessage, String responseString) {
                        Log.i(TAG, "!!! 成功:" + result.count);
                        alert("!!成功" + result.count);
                    }
                })
                .excute();
                
                
#处理需要显示进度条的情形

 RequestBuilder.with(getActivity())
                .URL(Apis.GAEA_URLS.CAB_NOTICE_LIST)
                .para("cabinet_code", "1412345678")
                        /******** 没错，你没有看错，仅仅 下面 一行，进度条就闪亮登场 ************/
                .progress(new DialogProgressIndicator(getActivity()))
                        /******** 没错，你没有看错，仅仅 上面 一行，进度条就闪亮登场 ************/
                .onSuccess(new CommonCallback<Demo3Bean>(Demo3Bean.class) {
                    @Override
                    public void onSuccess(Demo3Bean result, CommonMessage responseMessage, String responseString) {
                        Log.i(TAG, "!!! 成功:" + result.count);
                        alert("!!成功" + result.count);
                    }
                })
                .excute();
                
                
                
# 同步的方式发送http请求
 
    private void demo_syncExcuete() {

        new AsyncTask<Void, Void, Void>() {
            boolean isok;
            String mResult1;

            @Override
            protected Void doInBackground(Void... params) {
                RequestBuilder.with(getActivity())
                        .URL(Apis.GAEA_URLS.CAB_ADVERT_LIST)
                        .para("cabinet_code", "1412345678")
                        .onSuccess(new CommonCallback<String>(String.class) {
                            @Override
                            public void onSuccess(String result, CommonMessage responseMessage, String responseString) {
                                isok = true;
                                mResult1 = result;
                            }

                            @Override
                            public boolean onFailure(int httpCode, Exception exception, CommonMessage responseMessage, String allResponseString) {
                                isok = false;
                                return super.onFailure(httpCode, exception, responseMessage, allResponseString);
                            }
                        })
                        .syncExcute();

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (isok) {
                    Log.i(TAG, "==成功:" + mResult1);
                    alert("==成功");
                }
            }
        }.execute();

    }
    
# 下载文件

    public static void downloadFileDemo() {
        String url = "http://d.hiphotos.baidu.com/zhidao/pic/item/08f790529822720e67a9065978cb0a46f21fab2a.jpg";
        File dest = new File(Environment.getExternalStorageDirectory(), "6f21fab2a.jpg");
        FileDownloader.downloadFile(url, dest, new FileDownloader.DownloadFileProgressListener2() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Err: " + e.getMessage());
            }

            @Override
            public void onProgress(long bytesRead, long contentLength, boolean done) {
                System.out.println(String.format("文件下载进度, read %s/%s", bytesRead, contentLength));
            }

            @Override
            protected void onSuccess(Call call, File file) {
                System.out.println("文件下载成功吗 =" + file.exists());

            }
        });
    }

    