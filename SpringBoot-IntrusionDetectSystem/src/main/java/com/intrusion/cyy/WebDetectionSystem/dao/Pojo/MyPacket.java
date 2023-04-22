package com.intrusion.cyy.WebDetectionSystem.dao.Pojo;

import org.springframework.stereotype.Repository;

@Repository
public class MyPacket {

    //源IP
    String SrcIp;
    //目标IP
    String DstIp;
    //源端口
    String SrcPort;
    //协议
    String Protocol;
    //目标端口
    String DstPort;

    Integer Length;

    public Integer getLength() {
        return Length;
    }

    public void setLength(Integer length) {
        Length = length;
    }

    @Override
    public String toString() {
        return "MyPacket{" +
                "SrcIp='" + SrcIp + '\'' +
                ", DstIp='" + DstIp + '\'' +
                ", SrcPort='" + SrcPort + '\'' +
                ", Protocol='" + Protocol + '\'' +
                ", DstPort='" + DstPort + '\'' +
                ", Length=" + Length +
                '}';
    }

    public String getSrcPort() {
        return SrcPort;
    }

    public void setSrcPort(String srcPort) {
        SrcPort = srcPort;
    }

    public String getDstPort() {
        return DstPort;
    }

    public void setDstPort(String dstPort) {
        DstPort = dstPort;
    }

    public String getProtocol() {
        return Protocol;
    }

    public void setProtocol(String protocol) {
        Protocol = protocol;
    }

    public String getSrcIp() {
        return SrcIp;
    }

    public void setSrcIp(String srcIp) {
        SrcIp = srcIp;
    }

    public String getDstIp() {
        return DstIp;
    }

    public void setDstIp(String dstIp) {
        DstIp = dstIp;
    }

}
