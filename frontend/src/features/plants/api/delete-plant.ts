import { useMutation, useQueryClient } from '@tanstack/react-query';
import { z } from 'zod';

import { api } from '@/lib/api-client';
import { MutationConfig } from '@/lib/react-query';
import { Plant } from '@/types/api';

import { getPlantsQueryOptions } from './get-plants';

export const deletePlantInputSchema = z.object({
  id: z.string().min(1, 'Required'),
});

export type DeletePlantInput = z.infer<typeof deletePlantInputSchema>;

export const deletePlant = ({
  data,
}: {
  data: DeletePlantInput;
}): Promise<Plant> => {
  const { id, ...rest } = data;
  return api.delete(`/plants/${data.id}`, rest);
};

type UseDeletePlantOptions = {
  mutationConfig?: MutationConfig<typeof deletePlant>;
};

export const useDeletePlant = ({
  mutationConfig,
}: UseDeletePlantOptions = {}) => {
  const queryClient = useQueryClient();

  const { onSuccess, ...restConfig } = mutationConfig || {};

  return useMutation({
    onSuccess: (...args) => {
      queryClient.invalidateQueries({
        queryKey: getPlantsQueryOptions().queryKey,
      });
      onSuccess?.(...args);
    },
    ...restConfig,
    mutationFn: deletePlant,
  });
};
