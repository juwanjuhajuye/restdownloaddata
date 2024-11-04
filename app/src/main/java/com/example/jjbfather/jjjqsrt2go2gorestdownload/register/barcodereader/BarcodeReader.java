package com.example.jjbfather.jjjqsrt2go2gorestdownload.register.barcodereader;

public class BarcodeReader {

    public native void setJNIBarCodeReader();

	static {
		System.loadLibrary("barcodereaderjni");
	}

    public void turnOnLaser()
	{
		setJNIBarCodeReader();
	}
}
