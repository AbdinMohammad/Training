package validate;

import bean.ErrorBean;
import data.model.L1Service;
import data.model.L2Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ValidateService {
    private static final Logger logger = LogManager.getLogger(ValidateService.class);
    private static final DecimalFormat df = new DecimalFormat("#.##");
    private List<ErrorBean> errorBeanList;

    public ValidateService() {
        df.setRoundingMode(RoundingMode.FLOOR);
        this.errorBeanList = new ArrayList<>();
    }

    public void validateData(List<L1Service> l1ServiceList, List<L2Service> l2ServiceList){
        for (L1Service l1: l1ServiceList ) {
            List<L2Service> l2Array = findById(l2ServiceList, l1.getDimCustomerId(), l1.getOrderNo());
            if (l2Array.size() == 1){
                validateRow(l1, l2Array.get(0));
            } else if (l2Array.size() <= 0) {
                ErrorBean errorBean = new ErrorBean(l1.getId().toString());
                errorBean.getErrorsList().add(String.format("the customer %s dose not exist on destination", l1.getDimCustomerId()));
                errorBeanList.add(errorBean);
            } else {
                ErrorBean errorBean = new ErrorBean(l1.getDimCustomerId());
                errorBean.getErrorsList().add(String.format("there are %s duplication for customer id %s" , l2Array.size(), l1.getDimCustomerId()));
                errorBeanList.add(errorBean);
            }
        }
        for (ErrorBean error: errorBeanList ) {
            logger.info("#### " +error.toString());
        }
    }

    private void validateRow(L1Service l1Service, L2Service l2Service){
        ErrorBean errorBean = new ErrorBean(String.valueOf(l1Service.getId()));
        if (l1Service.getProductType().equals(l1Service.getOrderType())){
            errorBean.addErrorToList(validateValue(l1Service.getOrderType(),l2Service.getOrderType(), "order_type"));
            errorBean.addErrorToList(validateValue(l1Service.getOrderNo(),l2Service.getOrderNo(), "order_no"));
            errorBean.addErrorToList(validateValue(l1Service.getDimBookingdateId(),l2Service.getDimBookingdateId(), "dim_bookingdate_id"));
            errorBean.addErrorToList(validateValue(l1Service.getDimCustomerId(),l2Service.getDimCustomerId(), "dim_customer_id"));
            errorBean.addErrorToList(validateValue(l1Service.getDimTotalsCurrency(),l2Service.getDimTotalsCurrency(), "dim_totals_currency"));
            errorBean.addErrorToList(validateValue(l1Service.getDimStatusId(),l2Service.getDimStatusId(), "dim_status_id"));
            errorBean.addErrorToList(validateValue(l1Service.getPhone(),l2Service.getPhone(), "phone"));
            errorBean.addErrorToList(validateValue(l1Service.getPaymentAmount(),l2Service.getPaymentAmount(), "payment_amount"));
            errorBean.addErrorToList(validateValue(l1Service.getDiscountAmount(),l2Service.getDiscountAmount(), "discount_amount"));
            errorBean.addErrorToList(validateValue(l1Service.getBaseAmount(),l2Service.getBaseAmount(), "base_amount"));
            errorBean.addErrorToList(validateValue(l1Service.getInputvat(),l2Service.getInputvat(), "inputvat"));
            errorBean.addErrorToList(validateValue(l1Service.getOutputvat(),l2Service.getOutputvat(), "outputvat"));
            errorBean.addErrorToList(validateValue(l1Service.getProductVat(),l2Service.getProductVat(), "product_vat"));
            errorBean.addErrorToList(validateValue(l1Service.getSellingPrice(),l2Service.getSellingPrice(), "selling_price"));
            errorBean.addErrorToList(validateValue(l1Service.getSellingPriceVat(),l2Service.getSellingPriceVat(), "selling_price_vat"));
            errorBean.addErrorToList(validateValue(l1Service.getIbv(),l2Service.getIbv(), "ibv"));
            errorBean.addErrorToList(validateValue(l1Service.getGbv(),l2Service.getGbv(), "gbv"));
            errorBean.addErrorToList(validateBusinessValue(df.format((l1Service.getGbv() == null ? 0:l1Service.getGbv()) * l1Service.getConversionRateUsd()),l2Service.getGbvUsd(), "gbv_usd"));
            errorBean.addErrorToList(validateBusinessValue(df.format((l1Service.getIbv() == null ? 0:l1Service.getIbv()) * l1Service.getConversionRateUsd()),l2Service.getIovUsd(), "iov_usd"));
        }
        if (l1Service.getProductName().equals("rule")){
            errorBean.addErrorToList(validateValue(l1Service.getProductName(),l2Service.getServiceFeeCode(), "service_fee_code"));
        }
        errorBeanList.add(errorBean);
    }

    private List<L2Service> findById (List<L2Service> l2ServiceList, String dimCustomerId, String orderNo){
        return  l2ServiceList.stream()
                .filter(l2 -> l2.getAttribute("dimCustomerId").equals(dimCustomerId))
                .filter(l2 -> l2.getAttribute("orderNo").equals(orderNo)).collect(Collectors.toList());
    }

    private  <T> String validateValue(T v1, T v2, String columnName){
        if (v1 == null){
            return String.format("the source value is null for column %s", columnName);
        }
        if (v2 == null){
            return String.format("the destination value is null for column %s", columnName);
        }
        if (!v1.equals(v2)){
            return String.format("miss match value between source and destination, columnName: %s, source: %s, dest: %s", columnName, v1, v2);
        }
        return null;
    }

    private  <T> String validateBusinessValue(T v1, T v2, String columnName){
        if (!v1.equals(v2)){
            return String.format("miss match value between destination and expected value, columnName: %s, expected: %s, dest: %s", columnName, v1, v2);
        }
        return null;
    }
}
