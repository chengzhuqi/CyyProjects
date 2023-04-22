package com.intrusion.cyy.WebDetectionSystem.controller;


import com.intrusion.cyy.WebDetectionSystem.utils.PacketUtils.NetworkInformation;
import com.intrusion.cyy.WebDetectionSystem.Handler.PacketHandler;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class Pcap4jController {
    PacketHandler packetHandler;

    @RequestMapping("/start")
    public void start(@RequestBody Map<String,String> data) {
        String filter = data.get("filter");
        String name = data.get("name");
        packetHandler = new PacketHandler(filter,name);
        Thread thread = new Thread(packetHandler);
        thread.setName("抓包线程");
        thread.start();
    }

    @GetMapping("/stop")
    public void stop() throws NotOpenException {
        packetHandler.stop(false);
    }

    @RequestMapping("/init")
    public List<String> initInforma() throws PcapNativeException {
        List<String> strings = new ArrayList<>();
        List<PcapNetworkInterface> networkInterfacesList = NetworkInformation.getNetworkInterfacesList();
        for (int i = 0; i < networkInterfacesList.size(); i++) {
            PcapNetworkInterface networkInterface = networkInterfacesList.get(i);
            String description = networkInterface.getDescription();
                strings.add(description);
        }
        return strings;
    }


}
