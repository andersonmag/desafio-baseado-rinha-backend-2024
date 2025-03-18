import * as React from "react";
import {useState} from "react";
import {Box, Button, FormControl, InputLabel, MenuItem, Select, TextField} from "@mui/material";
import {clientes} from "../../data/clientes.ts";
import {realizarTransacao} from "../../services/ClienteService";

export const Transacao = () => {
    const [formData, setFormData] = useState({
        descricao: "",
        tipo: "r",
        valor: "",
    });
    const [clienteId, setClienteId] = useState<number>(null);

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const response = await realizarTransacao(clienteId, formData);
            alert(`Saldo atualizado: ${response.data.saldo} \nAcesse a página de Extrato para ver detalhes.`);
        } catch (error) {
            alert(`${error.response.data.mensagem} \n\n ${error.response.data.campos ? JSON.stringify(error.response.data.campos) : ''}`);
        }
    }

    const handleChange = (event) => {
        const {name, value} = event.target;
        setFormData((prev) => ({...prev, [name as string]: value}));
    };

    return (
        <>
            <Box
                component="form"
                onSubmit={handleSubmit}
                sx={{display: "flex", flexDirection: "column", gap: 2, maxWidth: 400, margin: "auto"}}>

                <h1 style={{textAlign: 'center'}}>Realizar transação</h1>

                <TextField
                    label="Descrição"
                    name="descricao"
                    value={formData.descricao}
                    onChange={handleChange}
                    required
                />

                <FormControl>
                    <InputLabel>Tipo</InputLabel>
                    <Select name="tipo" value={formData.tipo} onChange={(ev) => handleChange(ev)} required
                            variant={"filled"}>
                        <MenuItem value="r">Recebíveis</MenuItem>
                        <MenuItem value="d">Débito</MenuItem>
                    </Select>
                </FormControl>

                <TextField
                    label="Valor"
                    name="valor"
                    type="number"
                    value={formData.valor}
                    onChange={handleChange}
                    required
                />

                <FormControl>
                    <InputLabel>Cliente</InputLabel>
                    <Select
                        value={clienteId}
                        onChange={(ev) => {
                            setClienteId(ev.target.value as number)
                        }}
                        required
                        variant="filled"
                    >
                        {clientes.map((cliente) => (
                            <MenuItem key={cliente.id} value={cliente.id}>
                                {cliente.id} - {cliente.nome}
                            </MenuItem>
                        ))}
                    </Select>
                </FormControl>

                <Button type="submit" variant="contained" color="primary">
                    Enviar
                </Button>
            </Box>
        </>
    );
};

export default Transacao;
