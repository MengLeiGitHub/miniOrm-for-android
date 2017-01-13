package com.test.http;

import com.async.http.android.CProxyRequester;
import com.async.http.annotation.JSONPOST;
import com.async.http.annotation.param.Param;

/**
 * Created by admin on 2016-12-22.
 */

public interface CustomerInterface {

    @JSONPOST("rest/factory/customer/get.do")
    CProxyRequester  test(@Param("factoryId")int factoryId,@Param("currentPage")int currentPage,
                          @Param("pageRow")int pageRow,
                          @Param("totalPage")int totalPage,
                          @Param("totalRow")int totalRow
                          );
}
