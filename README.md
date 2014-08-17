AndroidIPQQWry
==============

A simple JNI called library in android used to get Location of an IP address by query qqwry IP database. 


* .QQWry.dat is a IP location database contains more than 40,000 IP location datas, mainly 
Chinese addresses, this project is to provide a way to query the database, please check the
official website to get the latest data file http://www.cz88.net/ <br>

###1.Introduction
* This is a small free library with simple function to query qqwry.dat
* It is a jni call library
* The c++ source code is based on other open source project.
* The 7zip extract used AndroidUn7zip, for more information please check
https://github.com/hzy3774/AndroidUn7zip

###2.Usage

1. You can download all the demo source code and compile it.<br>
2. if you don't want to compile the c code, just use the library.


####code:

    public QQWryAnd(String datPath);    //construct
    public String getVersion();         //version info
    public String getIpAddr(String ip);
    public String getIpRange(String ip);
    public int getIpCount();
    public void close();        //release resources
####Demo screenshoot
![image](https://raw.githubusercontent.com/hzy3774/AndroidIPQQWry/master/image/demo_screen.gif)
####JNI log
![image](https://raw.githubusercontent.com/hzy3774/AndroidIPQQWry/master/image/jnilog.png)


###3.More information
* Basic structure of qqwry.dat http://hzy3774.iteye.com/blog/1851364 <br>

###4.More about me
* [ITeye blog: http://hzy3774.iteye.com/](http://hzy3774.iteye.com/)
* [Baidu blog: http://hi.baidu.com/hzyws](http://hi.baidu.com/hzyws)
* [Sina weibo: http://weibo.com/hzy3774](http://weibo.com/hzy3774)

###5.Contact to me
* QQ: [377406997](http://wpa.qq.com/msgrd?v=3&uin=377406997&site=qq&menu=yes)
* Email: [hzy3774@qq.com](mailto:hzy3774@qq.com)

