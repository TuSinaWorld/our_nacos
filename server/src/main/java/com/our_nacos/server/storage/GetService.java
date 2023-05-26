package com.our_nacos.server.storage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.our_nacos.server.bean.BeatInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class GetService {

    @Autowired
    private ServiceStorage serviceStorage;

    public BeatInfo GetServiceName(String serviceName) throws JsonProcessingException {
        int index=0;
        Map<String, BeatInfo> beatInfoList = serviceStorage.getBeatInfoList("res-food");

        ObjectMapper mapper=new ObjectMapper();
        CollectionType listType =
                mapper.getTypeFactory().constructCollectionType(ArrayList.class, BeatInfo.class);
        String JSON = null;
        try {
            JSON = mapper.writeValueAsString(beatInfoList.values());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        List<BeatInfo> list = mapper.readValue(JSON, listType);

         Long max=list.get(0).getFreeSpace();
         for(int i=0;i<list.size();i++){
             if(max<list.get(i).getFreeSpace()){
                 max=list.get(i).getFreeSpace();
                 index=i;
             }
         }
         return list.get(index);
    }
}
