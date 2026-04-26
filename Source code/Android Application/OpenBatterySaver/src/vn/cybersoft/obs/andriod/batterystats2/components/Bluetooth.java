package vn.cybersoft.obs.andriod.batterystats2.components;

import java.io.IOException;
import java.io.OutputStreamWriter;

import vn.cybersoft.obs.andriod.batterystats2.PowerNotifications;
import vn.cybersoft.obs.andriod.batterystats2.service.IterationData;
import vn.cybersoft.obs.andriod.batterystats2.service.PowerData;
import vn.cybersoft.obs.andriod.batterystats2.util.NotificationService;
import vn.cybersoft.obs.andriod.batterystats2.util.Recycler;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;

public class Bluetooth extends PowerComponent {
	public static class BluetoothData extends PowerData {
		private static Recycler<BluetoothData> recycler = new Recycler<BluetoothData>();

		public static BluetoothData obtain() {
			BluetoothData result = recycler.obtain();
			if(result != null) return result;
			return new BluetoothData();
		}

		@Override
		public void recycle() {
			recycler.recycle(this);
		}

		public boolean bluetoothOn;

		private BluetoothData() {}

		public void init(boolean bluetoothOn) {
			this.bluetoothOn = bluetoothOn;
		}

		public void writeLogDataInfo(OutputStreamWriter out) throws IOException {
			out.write("Bluetooth-on " + bluetoothOn + "\n");
		}
	}

	private BluetoothAdapter bluetoothAdapter;
	private PowerNotifications bluetoothNotif;
	private boolean isBluetoothOn;

	public Bluetooth(Context context) {
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		isBluetoothOn = bluetoothAdapter != null && bluetoothAdapter.isEnabled();

		if (NotificationService.available()) {
			bluetoothNotif = new NotificationService.DefaultReceiver() {
				@Override
				public void noteBluetoothOn() {
					isBluetoothOn = true;
				}

				@Override
				public void noteBluetoothOff() {
					isBluetoothOn = false;
				}
			};
			NotificationService.addHook(bluetoothNotif);
		}
	}

	@Override
	protected void onExit() {
		if (bluetoothNotif != null) {
			NotificationService.removeHook(bluetoothNotif);
		}
	}

	@Override
	public IterationData calculateIteration(long iteration) {
		IterationData result = IterationData.obtain();
		BluetoothData data = BluetoothData.obtain();

		if (bluetoothAdapter != null) {
            // Note: fallback to adapter if hooks missed or not available
            isBluetoothOn = bluetoothAdapter.isEnabled();
        }

		data.init(isBluetoothOn);
		result.setPowerData(data);
		return result;
	}

	@Override
	public boolean hasUidInformation() {
		return false;
	}

	@Override
	public String getComponentName() {
		return "Bluetooth";
	}
}
