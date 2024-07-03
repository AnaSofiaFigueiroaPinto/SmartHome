import React from "react";
import {useNavigate, useParams} from "react-router-dom";
import DeviceMeasurements from "./DeviceMeasurements";
import {IconButton} from "@mui/material";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";

function Measurements() {
    const { deviceId } = useParams();
    const searchParams = new URLSearchParams(window.location.search);
    const timestampStart = searchParams.get('givenStart');
    const timestampEnd = searchParams.get('givenEnd');
    const navigate = useNavigate();


    return (
        <div id="measurements" className="section">
            <DeviceMeasurements deviceId={deviceId} givenStart={timestampStart} givenEnd={timestampEnd} />
        </div>
    );
}

export default Measurements;