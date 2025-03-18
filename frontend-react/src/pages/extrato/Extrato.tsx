import * as React from 'react';
import {useEffect, useState} from 'react';
import {Box, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from "@mui/material";
import {getExtratoCliente} from "../../services/ClienteService.ts";
import {Cliente} from "../../data/cliente.ts";

interface Extrato {
    saldo: {
        total: number;
        limite: number;
    },
    ultimas_transacoes: {
        realizada_em: string;
        descricao: string;
        tipo: string;
        valor: number;
    }[]
}

const Extrato = () => {
    const [extrato, setExtrato] = useState<Extrato>({
        saldo: {total: 0, limite: 0},
        ultimas_transacoes: []
    });
    const [cliente, setCliente] = useState<Cliente>(null);

    useEffect(() => {
        const storedCliente = localStorage.getItem('cliente');
        if (storedCliente) {
            setCliente(JSON.parse(storedCliente));
        }

        const handleStorageChange = async () => {
            const clienteStorage = localStorage.getItem('cliente');
            if (clienteStorage) {
                const clienteConvertido = JSON.parse(clienteStorage);
                setCliente(JSON.parse(clienteStorage));
                await buscarExtrato(clienteConvertido.id);
            }
        };

        handleStorageChange();
        window.addEventListener('storage', handleStorageChange);

        return () => {
            window.removeEventListener('storage', handleStorageChange);
        };
    }, []);


    const buscarExtrato = async (idCliente) => {
        const response = await getExtratoCliente(idCliente);
        setExtrato(response.data);
    }

    return (
        <>
            <Box
                component="div"
                sx={{display: "flex", flexDirection: "column", gap: 2, maxWidth: 800, margin: "auto"}}>

                <h1 style={{textAlign: 'center'}}>Extrato</h1>

                <b><h3>{cliente?.id} - {cliente?.nome}</h3></b>
                <span>Saldo total: R$ {extrato.saldo.total}</span>
                <span>Limite: R$ {extrato.saldo.limite}</span>

                <p>Últimas 10 transações do cliente, ordenadas por data em ordem decrescente:</p>

                <TableContainer component={Paper}>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell>Data</TableCell>
                                <TableCell>Descrição</TableCell>
                                <TableCell>Tipo</TableCell>
                                <TableCell>Valor</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {extrato.ultimas_transacoes.map((transacao, index) => (
                                <TableRow key={index}>
                                    <TableCell>{transacao.realizada_em}</TableCell>
                                    <TableCell>{transacao.descricao}</TableCell>
                                    <TableCell>{transacao.tipo === "r" ? "Recebíveis" : "Débito"}</TableCell>
                                    <TableCell>R$ {transacao.valor}</TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            </Box>
        </>
    );
};

export default Extrato;
