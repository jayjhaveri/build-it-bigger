/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.example.Jay.myapplication.backend;

import com.example.JokeLibrary;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.Jay.example.com",
                ownerName = "backend.myapplication.Jay.example.com",
                packagePath = ""
        )
)
public class JokeEndpoint {

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    /*@ApiMethod(name = "sayHi")
    public JokeBean sayHi(@Named("name") String name) {
        JokeBean response = new JokeBean();
        response.setData("Hi, " + name);

        return response;
    }*/
    @ApiMethod(name = "sayJoke")
    public JokeBean sayJoke(){
        JokeBean jokeBean = new JokeBean();
        JokeLibrary jokeLibrary = new JokeLibrary();
        jokeBean.setData(jokeLibrary.getJoke());
        return jokeBean;
    }

}
