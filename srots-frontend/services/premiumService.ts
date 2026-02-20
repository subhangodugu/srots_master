import api from "./api";
import { AxiosResponse } from "axios";

export const PremiumService = {
    subscribe: (months: number, utr: string): Promise<AxiosResponse> =>
        api.post(`/premium/subscribe`, { months, utr }),
};
