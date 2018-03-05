package uk.co.ribot.androidboilerplate.data.remote;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MockInterceptor implements Interceptor {
    private static final String TAG = MockInterceptor.class.getSimpleName();
    private static final String FILE_EXTENSION = ".json";

    private String mContentType = "application/json";

    private Context mContext;

    public MockInterceptor(Application application) {
        mContext = application.getApplicationContext();
    }

    /**
     * Set content type for header
     * @param contentType Content type
     * @return FakeInterceptor
     */
    public MockInterceptor setContentType(final String contentType) {
        this.mContentType = contentType;
        return this;
    }

    @Override
    public Response intercept(@NonNull final Chain chain) throws IOException {
        final List<String> listSuggestionFileName = new ArrayList<>();
        final String method = chain.request().method().toLowerCase();

        Response response = null;
        // Get Request URI.
        final URI uri = chain.request().url().uri();
        Log.d(TAG, "--> Request url: [" + method.toUpperCase() + "]" + uri.toString());

        final String defaultFileName = getFileName(chain);

        //eg: login.json
        listSuggestionFileName.add(defaultFileName);

        final String responseFileName = getFirstFileNameExist(listSuggestionFileName, uri);
        if (responseFileName != null) {
            final String fileName = getFilePath(uri, responseFileName);
            Log.d(TAG, "Read data from file: " + fileName);
            try {
                final InputStream is = mContext.getAssets().open(fileName);
                final BufferedReader r = new BufferedReader(new InputStreamReader(is));
                final StringBuilder responseStringBuilder = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    responseStringBuilder.append(line).append('\n');
                }
                Log.d(TAG, "Response: " + responseStringBuilder.toString());
                response = new Response.Builder()
                        .code(200)
                        .message(responseStringBuilder.toString())
                        .request(chain.request())
                        .protocol(Protocol.HTTP_1_0)
                        .body(ResponseBody.create(MediaType.parse(mContentType),
                                responseStringBuilder.toString().getBytes()))
                        .addHeader("content-type", mContentType)
                        .build();
            } catch (final IOException e) {
                Log.wtf(TAG, e);
            }
        } else {
            for (final String file : listSuggestionFileName) {
                Log.e(TAG, "File not exist: " + getFilePath(uri, file));
            }
            response = chain.proceed(chain.request());
        }

        Log.d(TAG, "<-- END [" + method.toUpperCase() + "]" + uri.toString());
        return response;
    }

    private String getFirstFileNameExist(final List<String> inputFileNames,
                                         final URI uri) throws IOException {
        String mockDataPath = uri.getHost() + uri.getPath();
        mockDataPath = mockDataPath.substring(0, mockDataPath.lastIndexOf('/'));
        Log.d(TAG, "Scan files in: " + mockDataPath);
        //List all files in folder
        final String[] files = mContext.getAssets().list(mockDataPath);
        for (final String fileName : inputFileNames) {
            if (fileName != null) {
                for (final String file : files) {
                    if (fileName.equals(file)) {
                        return fileName;
                    }
                }
            }
        }
        return null;
    }

    private static String getFileName(final Chain chain) {
        final String fileName = chain.request().url().pathSegments()
                .get(chain.request().url().pathSegments().size() - 1);
        return fileName.isEmpty() ? "index" + FILE_EXTENSION : fileName + FILE_EXTENSION;
    }

    private static String getFilePath(final URI uri, final String fileName) {
        final String path;
        if (uri.getPath().lastIndexOf('/') != uri.getPath().length() - 1) {
            path = uri.getPath().substring(0, uri.getPath().lastIndexOf('/') + 1);
        } else {
            path = uri.getPath();
        }
        return uri.getHost() + path + fileName;
    }
}