package me.saru.jobfinder.scraping;

import net.jodah.failsafe.Failsafe;
import net.jodah.failsafe.RetryPolicy;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class Retry {
    private static final RetryPolicy HTTP_200_RETRY = new RetryPolicy()
            .retryIf((Response response) -> {
                if (!response.isSuccessful()) {
                    // Close the body if its not successful
                    response.close();
                    return true;
                }
                return false;
            })
            .withBackoff(500, 5000, TimeUnit.MILLISECONDS)
            .withMaxRetries(3);

    private Retry() {
    }

    public static String retryUntilSuccessfulWithBackoff(Callable<Response> callable) {
        try (Response response = Failsafe.with(HTTP_200_RETRY).get(callable)) {
            return response.body().string();
        } catch (IOException e) {
            throw Exceptions.sneaky(e);
        }
    }
}
