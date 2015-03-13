/**
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License. 
 *  
 */
package org.apache.kerby.kerberos.kdc.server;

import org.apache.kerby.config.Conf;
import org.apache.kerby.config.Config;
import org.apache.kerby.kerberos.kdc.identitybackend.LdapIdentityBackend;
import org.apache.kerby.kerberos.kerb.identity.IdentityService;
import org.apache.kerby.kerberos.kerb.server.KdcServer;

import java.io.File;
import java.io.IOException;

/**
 * The mentioned Kerby KDC server implementation
 */
public class KerbyKdcServer extends KdcServer {

    public KerbyKdcServer() {
        super();
    }

    public void init() {
        super.init();
        initIdentityService();
    }

    public void init(String confDir, String workDir) throws IOException {
        init();
        initConfig(confDir);
    }

    /**
     * init config from configuration file
     */
    private void initConfig(String confDirString) throws IOException {
        Conf conf = kdcConfig.getConf();

        File confDir = new File(confDirString);
        File[] files = confDir.listFiles();
        for (File file : files) {
            conf.addIniConfig(file);
        }

    }

    private static KerbyKdcServer server;
    private static final String USAGE = "Usage: " + KerbyKdcServer.class.getSimpleName() + " -start conf-dir working-dir|-stop";

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println(USAGE);
            return;
        }

        if (args[0].equals("-start")) {
            if (args.length != 3) {
                System.err.println(USAGE);
                return;
            }
            String confDir = args[1];
            String workDir = args[2];

            server = new KerbyKdcServer();
            try {
                server.init(confDir, workDir);
            } catch (IOException e) {
                System.err.println("Something wrong with configuration files or work files");
                return;
            }
            server.start();
            System.out.println(KerbyKdcServer.class.getSimpleName() + " started.");
        } else if (args[0].equals("-stop")) {
            //server.stop();//FIXME can't get the server instance here
            System.out.println("KDC Server stoped.");
        } else {
            System.err.println(USAGE);
        }

    }

    protected void initIdentityService() {
        Config config = getKdcConfig().getBackendConfig();
        IdentityService identityService = new LdapIdentityBackend(config);
        setIdentityService(identityService);
    }
}