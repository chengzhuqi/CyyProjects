package com.intrusion.cyy.WebDetectionSystem.utils.PacketUtils;


import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.Pcaps;

import java.util.List;

public class NetworkInformation {

    public static List<PcapNetworkInterface> getNetworkInterfacesList() throws PcapNativeException {
        return Pcaps.findAllDevs();
    }

    public static PcapNetworkInterface getRequiredNetworkInterface(String NetWorkName) throws PcapNativeException {
        List<PcapNetworkInterface> networkInterfacesList = getNetworkInterfacesList();
        for (int i = 0; i < networkInterfacesList.size() ; i++){
            PcapNetworkInterface networkInterface = networkInterfacesList.get(i);
            if (NetWorkName.equals(networkInterface.getDescription())){
                    return networkInterfacesList.get(i);
            }
        }
        return networkInterfacesList.get(1);
    }


}
