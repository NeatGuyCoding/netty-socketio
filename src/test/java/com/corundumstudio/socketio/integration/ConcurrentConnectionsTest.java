/**
 * Copyright (c) 2012-2025 Nikita Koksharov
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.corundumstudio.socketio.integration;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.corundumstudio.socketio.listener.ConnectListener;

import io.socket.client.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for SocketIO concurrent connections functionality.
 */
@DisplayName("Concurrent Connections Tests - SocketIO Protocol CONNECT (Multiple Clients)")
public class ConcurrentConnectionsTest extends AbstractSocketIOIntegrationTest {

    @Test
    @DisplayName("Should handle multiple concurrent client connections successfully")
    public void testConcurrentConnections() throws Exception {
        // Test multiple concurrent connections
        int clientCount = 5;
        CountDownLatch connectLatch = new CountDownLatch(clientCount);
        AtomicInteger connectedClients = new AtomicInteger(0);

        getServer().addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(com.corundumstudio.socketio.SocketIOClient client) {
                connectedClients.incrementAndGet();
                connectLatch.countDown();
            }
        });

        // Create and connect multiple clients
        Socket[] clients = new Socket[clientCount];
        for (int i = 0; i < clientCount; i++) {
            clients[i] = createClient();
            clients[i].connect();
        }

        // Wait for all connections
        assertTrue(connectLatch.await(15, TimeUnit.SECONDS), "All clients should connect within 15 seconds");
        assertEquals(clientCount, connectedClients.get(), "All clients should be connected");
        assertEquals(clientCount, getServer().getAllClients().size(), "Server should have all clients connected");

        // Cleanup all clients
        for (Socket client : clients) {
            client.disconnect();
            client.close();
        }
    }
}
