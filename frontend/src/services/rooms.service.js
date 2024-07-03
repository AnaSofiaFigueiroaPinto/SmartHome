import { API_URL } from "../config/requests";
import axios from "axios";

export const getRoomsList = async () => {
    const { data } = await axios.get(`${API_URL}/house/House001/rooms`);
    return data;
}

export const getRoomDetails = async (roomName) => {
    const { data } = await axios.get(`${API_URL}/rooms/${roomName}`);
    return data;
}

export const getRoomDevices = async (roomId) => {
    const { data } = await axios.get(`${API_URL}/rooms/${roomId}/devices`);
    return data._embedded.deviceDTOList;
}