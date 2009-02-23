/* 
 * Copyright 2008 The Lichen Team.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package lichen.services.cache.memcache.impl;

import java.io.File;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;

import lichen.services.cache.memcache.impl.MemcachePoolConfig.Server;
import lichen.services.config.impl.JAXBUtil;

import org.testng.annotations.Test;

/**
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
public class MemcachePoolConfigTest {

	@Test
	public void test_write() throws Exception {
		File outDir = new File("test-output");
		if (!outDir.exists()) {
			outDir.mkdirs();
		}
		MemcachePoolConfig pconfig = new MemcachePoolConfig();
		List<Server> servers = new LinkedList<Server>();
		for (int i = 0; i < 2; i++) {
			Server server = new Server();
			server.setAddress("localhost");
			server.setPort(30002 + i);
			servers.add(server);
		}
		pconfig.setServers(servers);
		File xml = new File(outDir, "pool.xml");
		JAXBUtil.save(pconfig, new FileWriter(xml));
		
		MemcacheClientConfig cconfig = new MemcacheClientConfig();
		xml = new File(outDir, "client.xml");
		JAXBUtil.save(cconfig, new FileWriter(xml));
		
		List<MemcachePoolConfig> pools = new LinkedList<MemcachePoolConfig>();
		List<MemcacheClientConfig> clients = new LinkedList<MemcacheClientConfig>();
		pools.add(pconfig);
		pools.add(pconfig);
		
		clients.add(cconfig);
		clients.add(cconfig);
		
		MemcacheConfig config = new MemcacheConfig();
		config.setClient(clients);
		config.setPool(pools);
		
		xml = new File(outDir, "memcache.xml");
		JAXBUtil.save(config, new FileWriter(xml));
	}

}
