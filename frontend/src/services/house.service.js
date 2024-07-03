import { API_URL } from "../config/requests";
import axios from "axios";

export const getHouse = async () => {
    const { data } = await axios.get(`${API_URL}/house`);
    return data;
}

export const getHouseDevices = async () => {
    const { data } = await axios.get(`${API_URL}/house/House001/devices`);
    return data;
}

export const getSunriseTime = async () => {
    const { data } = await axios.get(`${API_URL}/house/weather?option=sunrise`);
    return data;
}

export const getSunsetTime = async () => {
    const { data } = await axios.get(`${API_URL}/house/weather?option=sunset`);
    return data;
}

export const getTemperature = async (currentHour) => {
    const { data } = await axios.get(`${API_URL}/house/weather?hour=${currentHour}`);
    return data;
}

export const getHumidity = async () => {
    const { data } = await axios.get(`${API_URL}/devices/SolarWatchHygrometer?functionality=HumidityPercentage`);
    return data;
}
