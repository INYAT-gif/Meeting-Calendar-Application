import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Login from './components/Login';
import Calendar from './components/Calendar';
import MeetingForm from './components/MeetingForm';

const App = () => {
    const [user, setUser] = useState(null);

    const handleLogin = (userData) => {
        setUser(userData);
    };

    return (
        <Router>
            <div>
                {!user ? (
                    <Login onLogin={handleLogin} />
                ) : (
                    <>
                        <MeetingForm onMeetingAdded={() => {}} />
                       
