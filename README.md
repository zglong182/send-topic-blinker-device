### 通过URL触发点灯设备的函数

1.  点灯账号,添加两个设备A和设备B
    > 两个设备必须使用同一账号
2.  一块esp32或esp8266 且写入点灯程序,作为设备B
    > 参考代码: [点灯科技 (diandeng.tech)](https://diandeng.tech/doc/getting-start-arduino "点灯科技 (diandeng.tech)")
    >
    > 设备额度不够,可在PC端登录后,绑定GITHUB,即可增至6个免费设备
3.  运行程序,发起请求

    请求Url:http://localhost:10030/send?sourceAuthKey=设备A的AuthKey&targetAuthKey=设备B的AuthKey&btnKey=要触发的按钮的key
    > 发起请求相当于手动点击该按钮

    
Siri远程唤醒
> 其他智能语音助手请参考:https://diandeng.tech/doc/voice-assistant

1. 添加快捷指令
2. 访问指定URL