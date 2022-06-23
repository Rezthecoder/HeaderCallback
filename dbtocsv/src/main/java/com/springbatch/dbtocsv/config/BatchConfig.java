package com.springbatch.dbtocsv.config;

import com.springbatch.dbtocsv.model.SalesRecord;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.RowMapper;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

  @Autowired
  JobBuilderFactory jobBuilderFactory;

  @Autowired
  StepBuilderFactory stepBuilderFactory;

  @Autowired
  DataSource dataSource;

  @Bean
  public JdbcCursorItemReader<SalesRecord> reader() {
    JdbcCursorItemReader<SalesRecord> reader = new JdbcCursorItemReader<>();
    reader.setDataSource(dataSource);
    // reader.setSql("SELECT country,item_type,order_id,units_sold,unit_price FROM sales_records");
    reader.setSql("SELECT country,item_type,order_id FROM sales_records");
    reader.setRowMapper(
      new RowMapper<SalesRecord>() {
        @Override
        public SalesRecord mapRow(ResultSet rs, int rowNum)
          throws SQLException {
          SalesRecord salesRecord = new SalesRecord();
          //   salesRecord.setCountry(rs.getString("country"));
          //   salesRecord.setItemType(rs.getString("item_type"));
          //   salesRecord.setOrderId(rs.getString("order_id"));
          //   salesRecord.setUnitsSold(rs.getString("units_sold"));
          //   salesRecord.setUnitPrice(rs.getString("unit_price"));
          //   return salesRecord;
          // }
          salesRecord.setCountry(rs.getString("country"));
          salesRecord.setItemType(rs.getString("item_type"));
          salesRecord.setOrderId(rs.getString("order_id"));
          return salesRecord;
        }
      }
    );
    return reader;
  }

  // @Bean
  // public FlatFileItemWriter<SalesRecord> writer() {
  //   FlatFileItemWriter<SalesRecord> writer = new FlatFileItemWriter<>();
  //   writer.setResource(new FileSystemResource("/Users/sureshlama/Desktop/newCsv/sales.csv"));
  //   // writer.setResource(new ClassPathResource("/sales.csv"));
  //   DelimitedLineAggregator<SalesRecord> aggregator = new DelimitedLineAggregator<>();
  //   BeanWrapperFieldExtractor<SalesRecord> fieldExtractor = new BeanWrapperFieldExtractor<>();
  //   fieldExtractor.setNames(
  //     new String[] {
  //       "country",
  //       "itemType",
  //       "orderId",
  //       "unitsSold",
  //       "unitPrice",
  //       "unitCost"
  //     }
  //   );
  //   aggregator.setFieldExtractor(fieldExtractor);
  //   writer.setLineAggregator(aggregator);

  //   return writer;
  // }

  @Bean
  public FlatFileItemWriter<SalesRecord> writer() {
    FlatFileItemWriter<SalesRecord> writer = new FlatFileItemWriter<>();
    // headercallback
    writer.setHeaderCallback(
      writer1 -> {
        writer1.write("Country,ItemsId,OrderId");
      }
    );
    writer.setResource(
      new FileSystemResource("/Users/sureshlama/Desktop/sales1.csv")
    );
    DelimitedLineAggregator<SalesRecord> aggregator = new DelimitedLineAggregator<>();
    BeanWrapperFieldExtractor<SalesRecord> fieldExtractor = new BeanWrapperFieldExtractor<>();
    fieldExtractor.setNames(new String[] { "country", "itemType", "orderId" });
    aggregator.setFieldExtractor(fieldExtractor);
    writer.setLineAggregator(aggregator);

    return writer;
  }

  @Bean
  public Step step1() {
    return stepBuilderFactory
      .get("step1")
      .<SalesRecord, SalesRecord>chunk(10)
      .reader(reader())
      .writer(writer())
      .build();
  }

  @Bean
  public Job job() {
    return jobBuilderFactory
      .get("job")
      .incrementer(new RunIdIncrementer())
      .flow(step1())
      .end()
      .build();
  }
}
