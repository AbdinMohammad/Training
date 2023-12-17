package data.model;

import javax.persistence.*;
import java.lang.reflect.Field;


@Entity
@Table(name = "L2Service")
public class L2Service {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_type")
    private String orderType;

    @Column(name = "product_type")
    private String productType;

    @Column(name = "dim_group_id")
    private Integer dimGroupId;

    @Column(name = "order_no")
    private String orderNo;

    @Column(name = "dim_bookingdate_id")
    private Integer dimBookingdateId;

    @Column(name = "dim_store_id")
    private Integer dimStoreId;

    @Column(name = "service_fee_code")
    private String serviceFeeCode;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "dim_customer_id")
    private String dimCustomerId;

    @Column(name = "dim_totals_currency")
    private String dimTotalsCurrency;

    @Column(name = "dim_status_id")
    private String dimStatusId;

    @Column(name = "phone")
    private String phone;

    @Column(name = "payment_amount")
    private Float paymentAmount;

    @Column(name = "discount_amount")
    private Float discountAmount;

    @Column(name = "service_fee_amount")
    private Float serviceFeeAmount;

    @Column(name = "base_amount")
    private Float baseAmount;

    @Column(name = "inputvat")
    private Float inputvat;

    @Column(name = "outputvat")
    private Float outputvat;

    @Column(name = "product_vat")
    private Float productVat;

    @Column(name = "selling_price")
    private Float sellingPrice;

    @Column(name = "selling_price_vat")
    private Float sellingPriceVat;

    @Column(name = "ibv")
    private Float ibv;

    @Column(name = "iov_usd")
    private Float iovUsd;

    @Column(name = "gbv")
    private Float gbv;

    @Column(name = "gbv_usd")
    private Float gbvUsd;

    public Long getId() {
        return id;
    }

    public String getOrderType() {
        return orderType;
    }

    public String getProductType() {
        return productType;
    }

    public Integer getDimGroupId() {
        return dimGroupId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public Integer getDimBookingdateId() {
        return dimBookingdateId;
    }

    public Integer getDimStoreId() {
        return dimStoreId;
    }

    public String getServiceFeeCode() {
        return serviceFeeCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getDimCustomerId() {
        return dimCustomerId;
    }

    public String getDimTotalsCurrency() {
        return dimTotalsCurrency;
    }

    public String getDimStatusId() {
        return dimStatusId;
    }

    public String getPhone() {
        return phone;
    }

    public Float getPaymentAmount() {
        return paymentAmount;
    }

    public Float getDiscountAmount() {
        return discountAmount;
    }

    public Float getServiceFeeAmount() {
        return serviceFeeAmount;
    }

    public Float getBaseAmount() {
        return baseAmount;
    }

    public Float getInputvat() {
        return inputvat;
    }

    public Float getOutputvat() {
        return outputvat;
    }

    public Float getProductVat() {
        return productVat;
    }

    public Float getSellingPrice() {
        return sellingPrice;
    }

    public Float getSellingPriceVat() {
        return sellingPriceVat;
    }

    public Float getIbv() {
        return ibv;
    }

    public Float getIovUsd() {
        return iovUsd;
    }

    public Float getGbv() {
        return gbv;
    }

    public Float getGbvUsd() {
        return gbvUsd;
    }

    public Object getAttribute(String attributeName) {
        try {
            Field field = this.getClass().getDeclaredField(attributeName);
            field.setAccessible(true);
            return field.get(this);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            throw new IllegalArgumentException("Invalid attribute name: " + attributeName);
        }
    }

    @Override
    public String toString() {
        return "L2Service{" +
                "id=" + id +
                ", orderType='" + orderType + '\'' +
                ", productType='" + productType + '\'' +
                ", dimGroupId=" + dimGroupId +
                ", orderNo='" + orderNo + '\'' +
                ", dimBookingdateId=" + dimBookingdateId +
                ", dimStoreId=" + dimStoreId +
                ", serviceFeeCode='" + serviceFeeCode + '\'' +
                ", productCode='" + productCode + '\'' +
                ", dimCustomerId='" + dimCustomerId + '\'' +
                ", dimTotalsCurrency='" + dimTotalsCurrency + '\'' +
                ", dimStatusId='" + dimStatusId + '\'' +
                ", phone='" + phone + '\'' +
                ", paymentAmount=" + paymentAmount +
                ", discountAmount=" + discountAmount +
                ", serviceFeeAmount=" + serviceFeeAmount +
                ", baseAmount=" + baseAmount +
                ", inputvat=" + inputvat +
                ", outputvat=" + outputvat +
                ", productVat=" + productVat +
                ", sellingPrice=" + sellingPrice +
                ", sellingPriceVat=" + sellingPriceVat +
                ", ibv=" + ibv +
                ", iovUsd=" + iovUsd +
                ", gbv=" + gbv +
                ", gbvUsd=" + gbvUsd +
                '}';
    }
}
