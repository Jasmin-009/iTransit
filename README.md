# 🚍 iTransit – Smart Bus Ticketing and Fraud Detection System

**iTransit** is a real-time, Android-based intelligent transportation system designed to digitize bus ticketing and detect potential conductor fraud. Built specifically for the **Surat Sitilink Bus Transportation System**, it leverages **Computer Vision (OpenCV)** and **Firebase** to track passenger headcount and compare it against ticket data in real-time.

---

## 📱 Screenshots

| Register | Login | Homepage |
|---------|--------|-----------|
| <img width="398" height="864" alt="1Register" src="https://github.com/user-attachments/assets/ea874799-5ece-4ec2-afd1-7bf3f0e54acf" /> | <img width="394" height="862" alt="2Login" src="https://github.com/user-attachments/assets/7e2e84f0-5682-4f4a-85bc-455618d5c396" /> | <img width="396" height="857" alt="3Homepage" src="https://github.com/user-attachments/assets/8eeb91a8-1bf8-4419-8e81-c279a2bceaee" /> |

| Navigation Drawer | Search Stop | Stop List |
|-------------------|-------------|------------|
| <img width="401" height="853" alt="4Drawer" src="https://github.com/user-attachments/assets/0842ef31-e638-4bee-ae6b-88e704372c65" /> | <img width="389" height="869" alt="5Search Stop" src="https://github.com/user-attachments/assets/9ed97543-5046-4cdc-8114-c3299741c043" /> | <img width="396" height="862" alt="6StopList" src="https://github.com/user-attachments/assets/7d4a7962-058d-4a7c-9e11-306465887f92" /> |

| Selected Stops | Available Buses | Book Ticket |
|----------------|------------------|-------------|
| <img width="399" height="860" alt="7Search Stop After Selecting" src="https://github.com/user-attachments/assets/1dbad44e-4a68-4129-9362-eb6089635199" /> | <img width="1080" height="2220" alt="8Available Bus with information" src="https://github.com/user-attachments/assets/f6e7f19b-8e47-4609-b493-32887bb8c3f2" /> | <img width="1080" height="2220" alt="9Book Ticket" src="https://github.com/user-attachments/assets/abf46e23-de35-442b-bf77-92e8e4fff945" /> |

---

## 🔍 Features

- 📲 **Android App** with user-friendly interface
- 🎟️ **E-Ticket generation system** with live booking
- 👥 **Real-time passenger headcount detection** using OpenCV
- ⚠️ **Fraud detection** by comparing ticket count vs. headcount
- 🧾 **Route Planner, Stop Search, and Fare Calculator**
- 🔐 Secure login and registration system with Firebase Auth
- 🌐 Web-based dashboard for admin monitoring and analytics

---

## 🚀 Tech Stack

- **Frontend:** Android (Java/Kotlin)
- **Backend:** Firebase Realtime Database & Authentication
- **Computer Vision:** OpenCV (Python integration)
- **Other Tools:** Android Studio, Google Maps API, QR Code Generator

---

## 📦 Installation

### Android App
1. Clone the repository
2. Open in Android Studio
3. Connect Firebase (replace `google-services.json`)
4. Run the app on an emulator or physical device

### Backend (Computer Vision Server)
1. Install Python and required packages
```bash
pip install opencv-python firebase-admin
