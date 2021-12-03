package com.eduardo.heroesapi.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static com.eduardo.heroesapi.constants.HeroesConstant.ENDPOINT_DYNAMO;
import static com.eduardo.heroesapi.constants.HeroesConstant.REGION_DYNAMO;

@Configuration
@EnableDynamoDBRepositories
public class HeroesTable {
    public static void main(String[] args) throws Exception {

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder
                        .EndpointConfiguration(ENDPOINT_DYNAMO, REGION_DYNAMO))
                .build();
        DynamoDB dynamoDB = new DynamoDB(client);
        String tableName = "Heroes_Table";

        try {
            Table table = dynamoDB.createTable(tableName,
                    List.of(new KeySchemaElement("id", KeyType.HASH)),
                    List.of(new AttributeDefinition("id", ScalarAttributeType.S)),
                    new ProvisionedThroughput(5l, 5l));
            table.waitForActive();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
}
