package org.apache.dubbo.qos.command.util;

import org.apache.dubbo.qos.command.GreetingCommand;
import org.apache.dubbo.qos.command.impl.ChangeTelnet;
import org.apache.dubbo.qos.command.impl.CountTelnet;
import org.apache.dubbo.qos.command.impl.DefaultMetricsReporterCmd;
import org.apache.dubbo.qos.command.impl.DisableDetailProfiler;
import org.apache.dubbo.qos.command.impl.DisableRouterSnapshot;
import org.apache.dubbo.qos.command.impl.DisableSimpleProfiler;
import org.apache.dubbo.qos.command.impl.EnableDetailProfiler;
import org.apache.dubbo.qos.command.impl.EnableRouterSnapshot;
import org.apache.dubbo.qos.command.impl.EnableSimpleProfiler;
import org.apache.dubbo.qos.command.impl.GetAddress;
import org.apache.dubbo.qos.command.impl.GetConfig;
import org.apache.dubbo.qos.command.impl.GetEnabledRouterSnapshot;
import org.apache.dubbo.qos.command.impl.GetOpenAPI;
import org.apache.dubbo.qos.command.impl.GetRecentRouterSnapshot;
import org.apache.dubbo.qos.command.impl.GetRouterSnapshot;
import org.apache.dubbo.qos.command.impl.GracefulShutdown;
import org.apache.dubbo.qos.command.impl.Help;
import org.apache.dubbo.qos.command.impl.InvokeTelnet;
import org.apache.dubbo.qos.command.impl.Live;
import org.apache.dubbo.qos.command.impl.LoggerInfo;
import org.apache.dubbo.qos.command.impl.Ls;
import org.apache.dubbo.qos.command.impl.Offline;
import org.apache.dubbo.qos.command.impl.OfflineApp;
import org.apache.dubbo.qos.command.impl.OfflineInterface;
import org.apache.dubbo.qos.command.impl.Online;
import org.apache.dubbo.qos.command.impl.OnlineApp;
import org.apache.dubbo.qos.command.impl.OnlineInterface;
import org.apache.dubbo.qos.command.impl.PortTelnet;
import org.apache.dubbo.qos.command.impl.PublishMetadata;
import org.apache.dubbo.qos.command.impl.PwdTelnet;
import org.apache.dubbo.qos.command.impl.Quit;
import org.apache.dubbo.qos.command.impl.Ready;
import org.apache.dubbo.qos.command.impl.SelectTelnet;
import org.apache.dubbo.qos.command.impl.SerializeCheckStatus;
import org.apache.dubbo.qos.command.impl.SerializeWarnedClasses;
import org.apache.dubbo.qos.command.impl.SetProfilerWarnPercent;
import org.apache.dubbo.qos.command.impl.ShutdownTelnet;
import org.apache.dubbo.qos.command.impl.Startup;
import org.apache.dubbo.qos.command.impl.SwitchLogLevel;
import org.apache.dubbo.qos.command.impl.SwitchLogger;
import org.apache.dubbo.qos.command.impl.Version;
import org.apache.dubbo.rpc.model.FrameworkModel;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommandHelperTest_Purified {

    private CommandHelper commandHelper = new CommandHelper(FrameworkModel.defaultModel());

    @Test
    void testHasCommand_1() {
        assertTrue(commandHelper.hasCommand("greeting"));
    }

    @Test
    void testHasCommand_2() {
        assertFalse(commandHelper.hasCommand("not-exiting"));
    }

    @Test
    void testGetCommandClass_1() {
        assertThat(commandHelper.getCommandClass("greeting"), equalTo(GreetingCommand.class));
    }

    @Test
    void testGetCommandClass_2() {
        assertNull(commandHelper.getCommandClass("not-exiting"));
    }
}
