import { API_URL } from "../config/requests";
import axios from "axios";

export const getBlindRollerList = async () => {
    const { data } = await axios.get(`${API_URL}/actuatorfunctionality/BlindSetter?type=map`);
    return data;
}

export const getCurrentState = async (deviceID) => {
    const { data } = await axios.get(`${API_URL}/devices/${deviceID}?functionality=Scale`);
    return data;
}

export const updateState = async (deviceID, newState) => {
    try {
        const response = await axios.patch(`${API_URL}/devices/${deviceID}?closePercentage=${newState}`);
        return response.data;
    } catch (error) {
        throw error;
    }
}