package com.hexadevlabs.gpt4allsample;

import com.hexadevlabs.gpt4all.LLModel;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args){

        String modelFilePath = "ggml-gpt4all-j-v1.3-groovy.bin";
        //String baseModelPath = "C:\\Users\\felix\\AppData\\Local\\nomic.ai\\GPT4All\\";
        // Alt path for WSL Linux
        String baseModelPath = "/mnt/c/Users/felix/AppData/Local/nomic.ai/GPT4All/";
        String librarySearchPath = "C:\\Users\\felix\\gpt4all\\lib\\";

        if(args.length>0){
            if(args[0].equals("mpt")){
                modelFilePath = "ggml-mpt-7b-instruct.bin";
            }else if(args[0].equals("llama")){
                modelFilePath = "ggml-vicuna-7b-1.1-q4_2.bin";
            }
        }
        // Optionally in case extra search path are necessary.
        LLModel.LIBRARY_SEARCH_PATH = librarySearchPath;

        Path modelPath = Path.of(baseModelPath + modelFilePath);

        LLModel.GenerationConfig config = LLModel.config()
                .withNPredict(4096).build();

        try ( LLModel model = new LLModel(modelPath) ){
            model.chatCompletion(
                    List.of(Map.of("role", "user", "content", "Add 2+2"),
                            Map.of("role", "assistant", "content", "4"),
                            Map.of("role", "user", "content", "Multiply 4 * 5")), config, true, true);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
