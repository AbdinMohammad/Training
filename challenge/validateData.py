import pandas as pd
import warnings

def main():
    report = {}
    with warnings.catch_warnings(record=True) as w:
        source_header = ["order_type","product_type","dim_group_id","order_no","dim_bookingdate_id","dim_store_id","ahs_group_name","product_name","product_code","dim_customer_id","dim_language","dim_totals_currency","dim_status_id","phone","payment_amount","discount_amount","base_amount","inputvat","outputvat","product_vat","selling_price","selling_price_vat","conversion_rate_sar","conversion_rate_usd","ibv","gbv"]
        source_df = load_file("C:\\Users\\moham\\Downloads\\l1_services.csv", source_header,",", True)
        dest_header = ["order_type","product_type","dim_group_id","order_no","dim_bookingdate_id","dim_store_id","service_fee_code","product_code","dim_customer_id","dim_language","dim_totals_currency","dim_status_id","phone","payment_amount","discount_amount","service_fee_amount","base_amount","inputvat","outputvat","product_vat","selling_price","selling_price_vat","ibv","iov_usd","gbv","gbv_usd"]
        dest_df = load_file("C:\\Users\\moham\\Downloads\\l2_services.csv", dest_header, ",", True)
        warning_messages = [str(warning.message) for warning in w]
    
    status, valid_rows = validate_data(source_df, dest_df)
    report['note'] = warning_messages[2]
    report["vaild_rows"] = valid_rows
    report["invaild_rows"] = status
    print(report)

def validate_data(source_df, dest_df):
    status = {}
    valid_rows = []
    for i in range(0,source_df.shape[0]):
        df_by_order_customer = dest_df.loc[(dest_df['dim_customer_id'] == source_df.iloc[i]['dim_customer_id']) & (dest_df['order_no'] == source_df.iloc[i]['order_no'])]
        no_custumer = len(df_by_order_customer)
        if (no_custumer == 1 ):
            r = validate_row(source_df.iloc[i], df_by_order_customer.iloc[0])
            if (len(r) > 0):
                status[i] = r
            else:
                valid_rows.append(i)
        elif (no_custumer <= 0):
            status[i] = f"the custmer id {source_df.iloc[i]['dim_customer_id']} dose not exsit on distination"
        elif (no_custumer > 1):
            status[i] = f"there are {no_custumer} duplication for custmer id {source_df.iloc[i]['dim_customer_id']}"
    return status, valid_rows

def validate_row(source_row, dest_row):
    report = []
    def check_value_by_column_name(column_name):
        if source_row[column_name] != dest_row[column_name]:
            report.append(f"{column_name}|{source_row[column_name]}|{dest_row[column_name]}")

    def check_value(column_name, source_value, dest_value):
        if source_value != dest_value:
            report.append(f"{column_name}|{source_value}|{dest_value}")

    def check_row_cond_int(column_name, source_value, dest_value):
        try:
            s_value = int(source_value)
        except:
            report.append(f"{column_name}|cant convert source value to int {source_value}")
            return
        try:
            d_value = int(dest_value)
        except:
            report.append(f"{column_name}|cant convert dest value to int {dest_value}")
            return
        if s_value != d_value:
            report.append(f"{column_name}|{s_value}|{d_value}")

    def check_row_cond_float(column_name, source_value, dest_value):
        try:
            s_value = float(source_value)
        except:
            report.append(f"{column_name}|cant convert source value to float {source_value}")
            return
        try:
            d_value = float(dest_value)
        except:
            report.append(f"{column_name}|cant convert dest value to float {dest_value}")
            return
        if s_value != d_value:
            report.append(f"{column_name}|{s_value}|{d_value}")

    if source_row['product_type'] == source_row['order_type']:
        check_value_by_column_name('order_type')
        check_value_by_column_name('order_no')
        check_row_cond_int('dim_bookingdate_id', source_row['dim_bookingdate_id'], dest_row['dim_bookingdate_id'])
        check_value_by_column_name('dim_customer_id')
        check_value_by_column_name('dim_totals_currency')
        check_value_by_column_name('dim_status_id')
        check_value_by_column_name('phone')
        check_row_cond_float('payment_amount', source_row['payment_amount'], dest_row['payment_amount'])
        check_row_cond_float('discount_amount', source_row['discount_amount'], dest_row['discount_amount'])
        #check_row('service_fee_amount')
        check_row_cond_float('base_amount', source_row['base_amount'], dest_row['base_amount'])
        check_row_cond_float('inputvat', source_row['inputvat'], dest_row['inputvat'])
        check_row_cond_float('outputvat', source_row['outputvat'], dest_row['outputvat'])
        check_row_cond_float('product_vat', source_row['product_vat'], dest_row['product_vat'])
        check_row_cond_float('selling_price', source_row['selling_price'], dest_row['selling_price'])
        check_row_cond_float('selling_price_vat', source_row['selling_price_vat'], dest_row['selling_price_vat'])
        check_row_cond_float('ibv', source_row['ibv'], dest_row['ibv'])
        check_row_cond_float('iov_usd', round((source_row['ibv'] * source_row['conversion_rate_usd']), 2), dest_row['iov_usd'])
        check_row_cond_float('gbv', source_row['gbv'], dest_row['gbv'])
        check_row_cond_float('gbv_usd', round((source_row['gbv'] * source_row['conversion_rate_usd']), 2), dest_row['gbv_usd'])
    if (source_row['product_type'] == 'rule'):
        check_value('service_fee_code', source_row['product_name'], dest_row['service_fee_code'])
    return report 

def load_file(file_path, header, delimiter, is_hedar):
    skipHeader = 1 if is_hedar else 0
    df = pd.read_csv(file_path, names=header, sep=delimiter, skiprows=skipHeader, index_col=False, error_bad_lines=False)
    return df


if __name__ == '__main__':
    main()
