package com.winterhavenmc.deathban;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.junit.jupiter.api.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SavageDeathBanTests {


	private ServerMock server;
	private PluginMain plugin;


	@BeforeEach
	void setUp() {
		// Start the mock server
		server = MockBukkit.mock();
		// Load your plugin
		plugin = MockBukkit.load(PluginMain.class);
	}

	@AfterEach
	void tearDown() {
		// Stop the mock server
		MockBukkit.unmock();
	}


	@Nested
	@DisplayName("Test mock objects.")
	class MockingTests {

		@Test
		@DisplayName("mock server not null.")
		void MockServerNotNull() {
			Assertions.assertNotNull(server);
		}

		@Test
		@DisplayName("mock plugin not null.")
		void MockPluginNotNull() {
			Assertions.assertNotNull(plugin);
		}
	}


}
