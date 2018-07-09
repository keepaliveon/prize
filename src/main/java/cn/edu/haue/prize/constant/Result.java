package cn.edu.haue.prize.constant;

/**
 * Created by 杨晋升 on 2018-07-03.
 */
public class Result<T> {

    private Integer resultCode;
    private T resultObj;

    public Result() {
        this.resultCode = ResultCode.RESULT_CODE_FAIL;
    }

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public T getResultObj() {
        return resultObj;
    }

    public void setResultObj(T resultObj) {
        this.resultObj = resultObj;
    }
}
