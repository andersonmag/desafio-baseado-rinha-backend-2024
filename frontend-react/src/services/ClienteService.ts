import axios, {AxiosRequestConfig} from "axios";

const API_URL = "http://localhost:8080/api/clientes";

export async function realizarTransacao(idCliente: number, dados: any): Promise<any> {
    return await axios.post(`${API_URL}/${idCliente}/transacoes`, dados, {
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
        },
    } as AxiosRequestConfig);
}

export async function getExtratoCliente(idCliente: number): Promise<any> {
    return await axios.get(`${API_URL}/${idCliente}/extrato`);
}
