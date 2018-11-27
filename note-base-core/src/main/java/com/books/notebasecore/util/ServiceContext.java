package com.books.notebasecore.util;

import java.io.Serializable;

public class ServiceContext implements Serializable {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 4632518319690857877L;

    private int               userId;

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int value) {
        this.userId = value;
    }

    private String userName;

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String value) {
        this.userName = value;
    }

    private String appSessionId;

    /**
     * 应用程序的会话ID。
     * <p>
     * 使用场景描述：<br />
     * SOA架构体系中，典型的用户请求执行流程如下：用户请求=&gt;WEB服务器（service的客户端）=&gt;Service服务端<br />
     * 一个用户请求到达web服务器之后，web服务器可能要多次调用不同服务的不同方法，web服务器程序会记录一些日志，例如服务方法调用是否成功、
     * 执行时间是否过长等， 每个服务方法在Service服务端也会记录一些日志。<br />
     * 有时排查问题的时候，需要能够从日志中对某个用户请求完整的执行过程进行分析，包括web服务器上的日志以及调用到的各个Service端的日志。<br
     * />
     * 
     * <p>
     * 解决方法：<br />
     * 1. Web服务器为每个HTTP请求分配一个唯一的标识符【HTTP请求会话ID】，对任何2个不同的请求，【HTTP请求会话ID】都不相同；<br />
     * 2. Web服务器程序的各个方法，如果需要记录日志，都在日志中输出这个【HTTP请求会话ID】；<br />
     * 3. Web服务器程序调用各个服务时，通过<code>appSessionId</code>将【HTTP请求会话ID】传给服务端；<br />
     * 4. 服务端执行过程中如果记录日志，都在日志中输出appSessionId；
     * 
     * @return
     */
    public String getAppSessionId() {
        return this.appSessionId;
    }

    /**
     * 应用程序的会话ID。
     * <p>
     * 使用场景描述：<br />
     * SOA架构体系中，典型的用户请求执行流程如下：用户请求=&gt;WEB服务器（service的客户端）=&gt;Service服务端<br />
     * 一个用户请求到达web服务器之后，web服务器可能要多次调用不同服务的不同方法，web服务器程序会记录一些日志，例如服务方法调用是否成功、
     * 执行时间是否过长等， 每个服务方法在Service服务端也会记录一些日志。<br />
     * 有时排查问题的时候，需要能够从日志中对某个用户请求完整的执行过程进行分析，包括web服务器上的日志以及调用到的各个Service端的日志。<br
     * />
     * 
     * <p>
     * 解决方法：<br />
     * 1. Web服务器为每个HTTP请求分配一个唯一的标识符【HTTP请求会话ID】，对任何2个不同的请求，【HTTP请求会话ID】都不相同；<br />
     * 2. Web服务器程序的各个方法，如果需要记录日志，都在日志中输出这个【HTTP请求会话ID】；<br />
     * 3. Web服务器程序调用各个服务时，通过<code>appSessionId</code>将【HTTP请求会话ID】传给服务端；<br />
     * 4. 服务端执行过程中如果记录日志，都在日志中输出appSessionId；
     * 
     * @param value
     */
    public void setAppSessionId(String value) {
        this.appSessionId = value;
    }

    private String clientIp;

    public String getClientIp() {
        return this.clientIp;
    }

    public void setClientIp(String value) {
        this.clientIp = value;
    }

    private String serverIp;

    public String getServerIp() {
        return this.serverIp;
    }

    public void setServerIp(String value) {
        this.serverIp = value;
    }
}