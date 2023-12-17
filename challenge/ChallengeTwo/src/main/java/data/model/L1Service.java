package data.model;

import javax.persistence.*;
import java.lang.reflect.Field;

@Entity
@Table(name = "L1Service")
public class L1Service {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = true)
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

    @Column(name = "ahs_group_name")
    private String ahsGroupName;

    @Column(name = "product_name")
    private String productName;
    @Column(name = "product_code")
    private String productCode;

    @Column(name = "dim_customer_id")
    private String dimCustomerId;

    @Column(name = "dim_language")
    private String dimLanguage;
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

    @Column(name = "service_fee_amount", nullable = true)
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
    @Column(name = "conversion_rate_usd")
    private Float conversionRateUsd;

    @Column(name = "conversion_rate_sar")
    private Float conversionRateSar;
    @Column(name = "ibv")
    private Float ibv;

    @Column(name = "gbv")
    private Float gbv;

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

    public String getAhsGroupName() {
        return ahsGroupName;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getDimCustomerId() {
        return dimCustomerId;
    }

    public String getDimLanguage() {
        return dimLanguage;
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

    public Float getConversionRateUsd() {
        return conversionRateUsd;
    }

    public Float getConversionRateSar() {
        return conversionRateSar;
    }

    public Float getIbv() {
        return ibv;
    }

    public Float getGbv() {
        return gbv;
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


}
