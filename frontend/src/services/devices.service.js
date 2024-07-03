import axios from "axios";
import {API_URL} from "../config/requests";

export const getDeviceDetails = async (deviceID) => {
    const { data } = await axios.get(`${API_URL}/devices/${deviceID}`);
    return data;
}

export const addDevice = async (device) => {
    const { data } = await axios.post(`${API_URL}/devices`,device);
    return data;
}

export const deactivateDevice = async (deviceID) => {
    const { data } = await axios.patch(`${API_URL}/devices/${deviceID}`);
    return data;
}

export const getDeviceMeasurements = async (deviceId, timestampStart, timestampEnd) => {
    const response = await axios.get(`${API_URL}/devices/${deviceId}?givenStart=${timestampStart}&givenEnd=${timestampEnd}`, {
    });
    return response.data;
}

export const addSensorToDevice = async (sensorDTO) => {
    const { data } = await axios.post(`${API_URL}/sensor`,sensorDTO);
    return data;
}

export const addActuatorToDevice = async (actuatorDTO) => {
    const { data } = await axios.post(`${API_URL}/actuators`,actuatorDTO);
    return data;
}

export const fetchSensorFunctionalities = async () => {
    const { data } = await axios.get(`${API_URL}/sensorfunctionality`);
    return data;
}

export const fetchActuatorFunctionalities = async () => {
    const { data } = await axios.get(`${API_URL}/actuatorfunctionality`);
    return data;
}