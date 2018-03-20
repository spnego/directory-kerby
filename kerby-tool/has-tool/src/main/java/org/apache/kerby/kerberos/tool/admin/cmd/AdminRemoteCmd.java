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
package org.apache.kerby.kerberos.tool.admin.cmd;

import org.apache.kerby.has.client.HasAuthAdminClient;
import org.apache.kerby.kerberos.kerb.KrbException;

public abstract class AdminRemoteCmd {

    private HasAuthAdminClient client;

    public AdminRemoteCmd(HasAuthAdminClient authHadminClient) {
        this.client = authHadminClient;
    }

    protected HasAuthAdminClient getAuthAdminClient() {
        return client;
    }

    /**
     * Execute the admin cmd.
     * @param input Input cmd to execute
     */
    public abstract void execute(String[] input) throws KrbException;
}
