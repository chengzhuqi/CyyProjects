package com.intrusion.cyy.WebDetectionSystem.Handler;

import com.intrusion.cyy.WebDetectionSystem.dao.Pojo.MyPacket;
import com.intrusion.cyy.WebDetectionSystem.utils.PacketUtils.NetworkInformation;
import com.intrusion.cyy.WebDetectionSystem.utils.Socket.WebSocketService;
import org.pcap4j.core.*;
import org.pcap4j.packet.*;
import org.springframework.beans.factory.annotation.Autowired;

public class PacketHandler implements Runnable {

    private MyPacket mypacket = new MyPacket();


    WebSocketService webSocketService = new WebSocketService();
    String filter;

    String name;

    boolean flag = true;

    public PacketHandler(String filter,String name){
        this.filter = filter;
        this.name = name;
    }
    
    @Override
    public void run() {
        PcapNetworkInterface networkInterface;
        try {
            networkInterface = NetworkInformation.getRequiredNetworkInterface(name);
        } catch (PcapNativeException e) {
            throw new RuntimeException(e);
        }
        int maxlength = 65536;
        int timeout = 10;
        PcapNetworkInterface.PromiscuousMode mode = PcapNetworkInterface.PromiscuousMode.PROMISCUOUS;
        PcapHandle handle;
        try {
            handle = networkInterface.openLive(maxlength,mode,timeout);
            handle.setFilter(filter, BpfProgram.BpfCompileMode.NONOPTIMIZE);
        } catch (PcapNativeException e) {
            throw new RuntimeException(e);
        } catch (NotOpenException e) {
            throw new RuntimeException(e);
        }

        while (flag) {
            try {
                Packet packet = handle.getNextPacket();
                //对数据包进行处理
                if (packet != null) {

                    IpV4Packet ipV4Packet = packet.get(IpV4Packet.class);
                    if (packet.contains(IpV4Packet.class)){
                        String p = ipV4Packet.getHeader().getProtocol().toString();
                        int z = p.indexOf("(");
                        int y = p.indexOf(")");
                        String protocol = p.substring(z+1, y);
                        String srcip = ipV4Packet.getHeader().getSrcAddr().toString().substring(1);
                        String dstip = ipV4Packet.getHeader().getDstAddr().toString().substring(1);
                        mypacket.setSrcIp(srcip);
                        mypacket.setDstIp(dstip);
                        mypacket.setProtocol(protocol);
                    }

                    IpV6Packet ipV6Packet = packet.get(IpV6Packet.class);
                    if (packet.contains(IpV6Packet.class)){
                        String p = ipV6Packet.getHeader().getProtocol().toString();
                        int z = p.indexOf("(");
                        int y = p.indexOf(")");
                        String protocol = p.substring(z+1, y);
                        String srcip = ipV6Packet.getHeader().getSrcAddr().toString().substring(1);
                        String dstip = ipV6Packet.getHeader().getDstAddr().toString().substring(1);
                        mypacket.setSrcIp(srcip);
                        mypacket.setDstIp(dstip);
                        mypacket.setProtocol(protocol);
                    }
//                    EthernetPacket ethernetPacket = packet.get(EthernetPacket.class);
                    if (packet.contains(TcpPacket.class)){
                        TcpPacket tcpPacket = packet.get(TcpPacket.class);
                        String srcP = tcpPacket.getHeader().getSrcPort().toString();
                        String dstP = tcpPacket.getHeader().getDstPort().toString();
                        int length = tcpPacket.length();
                        mypacket.setLength(length);
                        mypacket.setSrcPort(srcP);
                        mypacket.setDstPort(dstP);
                    }else if (packet.contains(UdpPacket.class)){
                        UdpPacket udpPacket = packet.get(UdpPacket.class);
                        String srcP = udpPacket.getHeader().getSrcPort().toString();
                        String dstP = udpPacket.getHeader().getDstPort().toString();
                        int length = udpPacket.length();
                        mypacket.setLength(length);
                        mypacket.setSrcPort(srcP);
                        mypacket.setDstPort(dstP);
                    }else if (packet.contains(IcmpV4CommonPacket.class)){
                        IcmpV4CommonPacket icmpV4CommonPacket = packet.get(IcmpV4CommonPacket.class);
                        mypacket.setProtocol("IcmpV4");
                    }else if (packet.contains(ArpPacket.class)){
                        ArpPacket arpPacket = packet.get(ArpPacket.class);
                        mypacket.setProtocol("Arp");
                    }else if (packet.contains(DnsPacket.class)){
                        DnsPacket dnsPacket = packet.get(DnsPacket.class);
                        mypacket.setProtocol("Dns");
                    }

                    //睡0.5再抓，防止前端页面崩溃
                    Thread.sleep(500);
                    if (mypacket != null){
                        webSocketService.sendPacket(mypacket);
                        System.out.println(mypacket.toString());
                    }

                }
            } catch (NotOpenException e) {
                System.err.println("网络连接已关闭");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
//            System.out.println(handle.isOpen());
            if (!handle.isOpen()){
                break;
            }
        }
    }
    
    public void stop(boolean flag) throws NotOpenException {
        this.flag = flag;
    }

    public MyPacket getMypacket(){
        return mypacket;
    }
}
