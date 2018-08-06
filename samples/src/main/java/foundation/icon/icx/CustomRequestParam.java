/*
 * Copyright 2018 theloop Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package foundation.icon.icx;

import foundation.icon.icx.transport.http.HttpProvider;
import foundation.icon.icx.transport.jsonrpc.RpcItem;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import java.io.IOException;

public class CustomRequestParam {

    public final String URL = "http://localhost:9000/api/v3";
    private final String scoreAddress = "cxca23d7fd434fd37d5cd01c7183adf7658375a6db";

    private IconService iconService;

    public CustomRequestParam() {
        HttpLoggingInterceptor loggning = new HttpLoggingInterceptor();
        loggning.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(loggning)
                .build();
        iconService = new IconService(new HttpProvider(httpClient, URL));
    }

    public void getBalance() throws IOException {
        String address = "hx4873b94352c8c1f3b2f09aaeccea31ce9e90bd31";
        Param params = new Param();
        params._owner = address;

        IcxCall<RpcItem> call = new IcxCall.Builder()
                .from(address)
                .to(scoreAddress)
                .method("balanceOf")
                .params(params)
                .build();

        RpcItem result = iconService.query(call).execute();
        System.out.println("balance:"+result);
    }

    class Param {
        public String _owner;
    }

    public static void main(String[] args) throws IOException {
        new CustomRequestParam().getBalance();
    }
}
