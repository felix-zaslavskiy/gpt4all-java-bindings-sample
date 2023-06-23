package com.hexadevlabs.gpt4allsample;

import com.hexadevlabs.gpt4all.LLModel;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args){

        // DIRECTION: Update the baseModelPath to the location of model files on your system.

        String modelFilePath = "ggml-gpt4all-j-v1.3-groovy.bin";

        String osName = System.getProperty("os.name").toLowerCase();
        boolean isWindows = osName.startsWith("windows");
        boolean isMac = osName.startsWith("mac os x");
        boolean isLinux = osName.startsWith("linux");

        String baseModelPath = null;
        if(isWindows){
            baseModelPath ="C:\\Users\\felix\\AppData\\Local\\nomic.ai\\GPT4All\\";
        } else if(isLinux){
            baseModelPath = "/mnt/c/Users/felix/AppData/Local/nomic.ai/GPT4All/";
        } else if(isMac) {
            baseModelPath = "/Users/fzaslavs/Library/Application Support/nomic.ai/GPT4All/";
        }


        // Optionally in case override to location of shared libraries is necessary
        //LLModel.LIBRARY_SEARCH_PATH = "C:\\Users\\felix\\gpt4all\\lib\\";

        if(args.length>0){
            switch (args[0]) {
                case "mpt":
                    modelFilePath = "ggml-mpt-7b-instruct.bin";
                    break;
                case "llama":
                    modelFilePath = "ggml-vicuna-7b-1.1-q4_2.bin";
                    break;
                case "replit":
                    modelFilePath = "ggml-replit-code-v1-3b.bin";
                    break;
            }
        }

        // Debut output format. In case you need it.
        // LLModel.OUTPUT_DEBUG=true;

        Path modelPath = Path.of(baseModelPath + modelFilePath);

        try ( LLModel model = new LLModel(modelPath) ){

            LLModel.GenerationConfig config = LLModel.config()
                    .withNPredict(4096).build();

            // String result = gptjModel.generate(prompt, config, true);
            model.chatCompletion(
                    List.of(Map.of("role", "system", "content", "You are a helpful assistant"),
                            Map.of("role", "user", "content", "Add 2+2")), config, true, true);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
