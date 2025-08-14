package com.corundumstudio.socketio.store;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import org.junit.After;
import org.junit.Test;
import org.testcontainers.containers.GenericContainer;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Test class for HazelcastStore using testcontainers
 */
public class HazelcastStoreTest extends AbstractStoreTest {

    private HazelcastInstance hazelcastInstance;

    @Override
    protected GenericContainer<?> createContainer() {
        return new CustomizedHazelcastContainer();
    }

    @Override
    protected Store createStore(UUID sessionId) throws Exception {
        CustomizedHazelcastContainer customizedHazelcastContainer = (CustomizedHazelcastContainer) container;
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.getGroupConfig().setName("dev").setPassword("dev-pass");
        clientConfig.getNetworkConfig().addAddress(
            customizedHazelcastContainer.getHost() + ":" + customizedHazelcastContainer.getHazelcastPort()
        );
        
        hazelcastInstance = HazelcastClient.newHazelcastClient(clientConfig);
        return new HazelcastStore(sessionId, hazelcastInstance);
    }

    @Override
    protected void cleanupStore() {
        if (hazelcastInstance != null) {
            hazelcastInstance.shutdown();
        }
    }

    @Test
    public void testHazelcastSpecificFeatures() {
        // Test that the store is actually using Hazelcast
        assertNotNull(store);
        
        // Test large object storage
        byte[] largeData = new byte[1024 * 1024]; // 1MB
        store.set("largeData", largeData);
        byte[] retrieved = store.get("largeData");
        assertNotNull(retrieved);
        assertEquals(largeData.length, retrieved.length);
    }
}
