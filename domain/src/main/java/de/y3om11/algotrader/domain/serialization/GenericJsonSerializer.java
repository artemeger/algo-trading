package de.y3om11.algotrader.domain.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class GenericJsonSerializer {

    /**
     * {@link ObjectMapper}
     */
    private final ObjectMapper mapper;

    /**
     * Default constructor
     */
    public  GenericJsonSerializer(){
        this.mapper = new ObjectMapper();
    }

    /**
     * Constructor allowing for a custom mapper
     */
    public GenericJsonSerializer(ObjectMapper mapper){
        this.mapper = mapper;
    }

    /**
     * Converts a JSON String to a Jackson annotated Object
     * @param jsonString String to be converted
     * @return a valid Jackson annotated Object
     */
    public <T> T getObjectFromString(Class<T> objClass, String jsonString) throws IOException {
        return mapper.readValue(jsonString, objClass);
    }

    /**
     * Converts a Jackson annotated Object to a JSON String
     * @param msg Object to be converted
     * @return String value of the Jackson annotated Object
     */
    public String getStringFromRequestObject(Object msg) throws JsonProcessingException {
        return mapper.writer().writeValueAsString(msg);
    }

    /**
     * Converts a Jackson annotated Object to a byte array
     * @param msg Object to be converted
     * @return byte array of the Object
     */
    public byte[] getBytesFromRequestObject(Object msg) throws JsonProcessingException {
        return mapper.writer().writeValueAsBytes(msg);
    }
}