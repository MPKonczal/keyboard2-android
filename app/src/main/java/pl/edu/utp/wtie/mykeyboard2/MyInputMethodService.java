package pl.edu.utp.wtie.mykeyboard2;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;
import java.util.Set;
import java.util.UUID;


public class MyInputMethodService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private KeyboardView keyboardView;
    private Keyboard keyboard;
    private Intent intent;
    private NfcAdapter nfcAdapter;
    private BluetoothAdapter bluetoothAdapter;
    private SendOrReceive sendOrReceive;
    private boolean autocommit = false;
    private static final String APP_NAME = "MyKeyboard2";
    private static final UUID MY_UUID = UUID.fromString("79da459c-3ef2-4347-8f8a-8503fa45aba1");


    @SuppressLint("InflateParams")
    @Override
    public View onCreateInputView() {
        keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard_view, null);
        keyboard = new Keyboard(this, R.xml.content_pad_1);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setOnKeyboardActionListener(this);
        return keyboardView;
    }

    @SuppressLint("InflateParams")
    public View onCreateSecondInputView() {
        keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard_view, null);
        keyboard = new Keyboard(this, R.xml.content_pad_2);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setOnKeyboardActionListener(this);
        return keyboardView;
    }

    @SuppressLint("InflateParams")
    public View onCreateThirdInputView() {
        keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard_view, null);
        keyboard = new Keyboard(this, R.xml.content_pad_3);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setOnKeyboardActionListener(this);
        return keyboardView;
    }


    @Override
    public void onPress(int i) {

    }

    @Override
    public void onRelease(int i) {

    }

    @SuppressWarnings("deprecation")
    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (getCurrentInputConnection() != null) {
            switch (primaryCode) {
                case 0:
                    if (audioManager != null) {
                        audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
                    }
                    insertText();
                    break;
                case 1:
                    if (audioManager != null) {
                        audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
                    }
                    MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.f1_2018_theme_song);
                    mediaPlayer.start();
                    break;
                case 2:
                    if (audioManager != null) {
                        audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
                    }
                    startCamera();
                    break;
                case 3:
                    if (audioManager != null) {
                        audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
                    }
                    String fileName = "f1.txt";
                    String text = "The Brazilian Grand Prix 2019 was very exciting!";
                    saveToFile(fileName, text);
                    break;
                case 4:
                    if (audioManager != null) {
                        audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
                    }
                    displayToast();
                    break;
                case 5:
                case 23:
                    if (audioManager != null) {
                        audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
                    }
                    setInputView(onCreateSecondInputView());
                    break;
                case 6:
                    if (audioManager != null) {
                        audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
                    }
                    checkNfcStatus();
                    break;
                case 7:
                    if (audioManager != null) {
                        audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
                    }
                    enableOrDisableNfc();
                    break;
                case 8:
                    if (audioManager != null) {
                        audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
                    }
                    openMailPage();
                    break;
                case 9:
                    if (audioManager != null) {
                        audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
                    }
                    startYouTube();
                    break;
                case 10:
                    if (audioManager != null) {
                        audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
                    }
                    setInputView(onCreateInputView());
                    break;
                case 11:
                    if (audioManager != null) {
                        audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
                    }
                    setInputView(onCreateThirdInputView());
                    break;
                case 12:
                    if (audioManager != null) {
                        audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
                    }
                    enableOrDisableBluetooth();
                    break;
                case 13:
                    if (audioManager != null) {
                        audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
                    }
                    listen();
                    break;
                case 14:
                    if (audioManager != null) {
                        audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
                    }
                    connect();
                    break;
                case 15:
                    if (audioManager != null) {
                        audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
                    }
                    String text1 = "Hello, I'm custom bluetooth keyboard!!!";
                    sendOrReceive.write(text1.getBytes());
                    break;
                case 16:
                    if (audioManager != null) {
                        audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
                    }
                    String text2 = "616000010000123";
                    sendOrReceive.write(text2.getBytes());
                    break;
                case 17:
                    if (audioManager != null) {
                        audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
                    }
                    String text3 = "616000010000124";
                    sendOrReceive.write(text3.getBytes());
                    break;
                case 18:
                    if (audioManager != null) {
                        audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
                    }
                    InputMethodManager inputMethodManager = (InputMethodManager)
                            getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
                    if (inputMethodManager != null) {
                        inputMethodManager.showInputMethodPicker();
                    }
                    break;
                case 19:
                    if (audioManager != null) {
                        audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
                    }
                    ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    String message = null;
                    if (clipboardManager != null) {
                        message = String.valueOf(clipboardManager.getText());
                    }
                    if (message != null) {
                        sendOrReceive.write(message.getBytes());
                    }
                    break;
                case 20:
                    if (audioManager != null) {
                        audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
                    }
                    checkAutocommitStatus();
                    break;
                case 21:
                    if (audioManager != null) {
                        audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
                    }
                    enableOrDisableAutocommit();
                    break;
                case 22:
                    if (audioManager != null) {
                        audioManager.playSoundEffect(AudioManager.FX_FOCUS_NAVIGATION_DOWN);
                    }
                    commitManually();
                    break;
                default:
                    Toast.makeText(this, "Application Error!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onText(CharSequence charSequence) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }


    private void insertText() {
        InputConnection inputConnection = getCurrentInputConnection();
        inputConnection.deleteSurroundingText(10000, 1000);
        inputConnection.commitText("Hello, I'm custom keyboard!", 1);
    }

    private void insertTextFromBluetooth(String text) {
        InputConnection inputConnection = getCurrentInputConnection();
        inputConnection.deleteSurroundingText(10000, 1000);
        inputConnection.commitText(text, 1);
        if (autocommit) {
            InputConnection connection = getCurrentInputConnection();
            final int options = getCurrentInputEditorInfo().imeOptions;
            final int actionId = options & EditorInfo.IME_MASK_ACTION;
            switch (actionId) {
                case EditorInfo.IME_ACTION_SEARCH:
                case EditorInfo.IME_ACTION_GO:
                case EditorInfo.IME_ACTION_SEND:
                    sendDefaultEditorAction(true);
                    break;
                default:
                    connection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
            }
        }
    }

    private void startCamera() {
        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void saveToFile(String fileName, String text) {
        try (FileOutputStream fileOutputStream = openFileOutput(fileName, MODE_PRIVATE)) {
            fileOutputStream.write(text.getBytes());
            Toast.makeText(this, "SAVED TO: " + getFilesDir() + "/" + fileName, Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "File not found!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error saving to file!", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayToast() {
        final String[] TOAST = {"Never give up!", "Hello :)", "Take care of yourself :D",
                "I greet you!", "It will be fine.", "Something special will happen today ..."};
        Random random = new Random();
        double randomNumber = random.nextDouble() * (TOAST.length - 1);
        int randomToast = (int) Math.round(randomNumber);
        Toast.makeText(this, TOAST[randomToast], Toast.LENGTH_SHORT).show();
    }

    private void checkNfcStatus() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this, "No NFC module", Toast.LENGTH_SHORT).show();
        } else if (nfcAdapter.isEnabled()) {
            Toast.makeText(this, "NFC module is enabled", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "NFC module is disabled", Toast.LENGTH_SHORT).show();
        }
    }

    private void enableOrDisableNfc() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this, "No NFC module", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please enable or disable the NFC module!", Toast.LENGTH_LONG).show();
//            intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS); // for api < 16
            intent = new Intent(Settings.ACTION_NFC_SETTINGS);      // for api level 16 and above
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private void openMailPage() {
        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://mail.utp.edu.pl/mail/index.php"));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void startYouTube() {
        intent = getPackageManager().getLaunchIntentForPackage("com.google.android.youtube");
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        startActivity(intent);
    }

    private void enableOrDisableBluetooth() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Device doesn't support Bluetooth", Toast.LENGTH_SHORT).show();
        } else if (!bluetoothAdapter.isEnabled()) {
            intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            bluetoothAdapter.disable();
            Toast.makeText(this, "Disabling Bluetooth", Toast.LENGTH_SHORT).show();
        }
    }

    private void listen() {
        ServerClass serverClass = new ServerClass();
        serverClass.start();
        Toast.makeText(this, "Listening enabled", Toast.LENGTH_SHORT).show();
    }

    private void connect() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        BluetoothDevice[] bluetoothDevices = new BluetoothDevice[pairedDevices.size()];
        int index = 0;
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice bluetoothDevice : pairedDevices) {
                bluetoothDevices[index] = bluetoothDevice;
                index++;
            }
        }
        ClientClass clientClass = new ClientClass(bluetoothDevices[0]);
        clientClass.start();
        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
    }

    private void playAndVibrate() {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.signal);
        mediaPlayer.start();
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            vibrator.vibrate(500);
        }
    }

    private void checkAutocommitStatus() {
        if (!autocommit) {
            Toast.makeText(this, "Autocommit is disabled", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Autocommit is enabled", Toast.LENGTH_SHORT).show();
        }
    }

    private void enableOrDisableAutocommit() {
        if (!autocommit) {
            autocommit = true;
            Toast.makeText(this, "Enabling autocommit", Toast.LENGTH_SHORT).show();
        } else {
            autocommit = false;
            Toast.makeText(this, "Disabling autocommit", Toast.LENGTH_SHORT).show();
        }
    }

    private void commitManually() {
        if (!autocommit) {
            InputConnection connection = getCurrentInputConnection();
            final int options = getCurrentInputEditorInfo().imeOptions;
            final int actionId = options & EditorInfo.IME_MASK_ACTION;
            switch (actionId) {
                case EditorInfo.IME_ACTION_SEARCH:
                case EditorInfo.IME_ACTION_GO:
                case EditorInfo.IME_ACTION_SEND:
                    sendDefaultEditorAction(true);
                    break;
                default:
                    connection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
            }
        }
    }


    private class ServerClass extends Thread {

        private BluetoothServerSocket bluetoothServerSocket;

        public ServerClass() {
            try {
                bluetoothServerSocket = bluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord(APP_NAME, MY_UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            BluetoothSocket bluetoothSocket = null;
            while (bluetoothSocket == null) {
                try {
                    bluetoothSocket = bluetoothServerSocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (bluetoothSocket != null) {
                    sendOrReceive = new SendOrReceive(bluetoothSocket);
                    sendOrReceive.start();
                    break;
                }
            }
        }
    }


    private class ClientClass extends Thread {

        private BluetoothDevice bluetoothDevice;
        private BluetoothSocket bluetoothSocket;

        public ClientClass(BluetoothDevice bluetoothDevice1) {
            bluetoothDevice = bluetoothDevice1;
            try {
                bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                bluetoothSocket.connect();
                sendOrReceive = new SendOrReceive(bluetoothSocket);
                sendOrReceive.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private class SendOrReceive extends Thread {

        private final BluetoothSocket bluetoothSocket;
        private final InputStream inputStream;
        private final OutputStream outputStream;

        public SendOrReceive(BluetoothSocket socket) {
            bluetoothSocket = socket;
            InputStream tempIn = null;
            OutputStream tempOut = null;
            try {
                tempIn = bluetoothSocket.getInputStream();
                tempOut = bluetoothSocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            inputStream = tempIn;
            outputStream = tempOut;
        }

        public void run() {
            while (true) {
                try {
                    byte[] buffer = new byte[1024];
                    inputStream.read(buffer);
                    String message = new String(buffer, 0, buffer.length);
                    insertTextFromBluetooth(message);
                    playAndVibrate();
                    if (autocommit) {
                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    startCamera();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void write(byte[] bytes) {
            try {
                outputStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
